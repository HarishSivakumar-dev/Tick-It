package com.harish.TickIt.services;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.harish.TickIt.Exceptions.UserAlreadyRegisteredException;
import com.harish.TickIt.dtos.UserDetailsDto;
import com.harish.TickIt.dtos.UserFeignResponse;
import com.harish.TickIt.dtos.UserLoginDto;
import com.harish.TickIt.dtos.UserProfileDto;
import com.harish.TickIt.dtos.UserRegDto;
import com.harish.TickIt.enums.Designation;
import com.harish.TickIt.feign.ProjectServiceFeign;
import com.harish.TickIt.feign.UserServiceFeign;
import com.harish.TickIt.models.EmployeeManagement;
import com.harish.TickIt.models.Roles;
import com.harish.TickIt.models.UserRegistration;
import com.harish.TickIt.repositories.EmployeeManagementRepo;
import com.harish.TickIt.repositories.RoleRepo;
import com.harish.TickIt.repositories.UserRegRepo;
import com.harish.TickIt.util.JwtUtil;
import jakarta.transaction.Transactional;

@Service
public class AuthService
{
	@Autowired
	private UserRegRepo rep;
	@Autowired
	private RoleRepo rolerep;
	@Autowired
	private UserServiceFeign userServiceFeign;
	@Autowired
	private ProjectServiceFeign project;
	@Autowired
	private EmployeeManagementRepo emr;
	
	BCryptPasswordEncoder bpe= new BCryptPasswordEncoder(12);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Transactional
	public String registerUser(UserRegDto dto)
	{
		EmployeeManagement employee= validateEmployee(dto);
		
		UserRegistration reg= createUser(dto, employee);
			
		String res= createUserProfile(reg, employee.getId());
		String resp= createProjectDetails(reg);
		
		System.out.println("Profile creation response: " + res);
		System.out.println("Project Table Population Response : " + resp);
		
		return "Registered";
		
	}
	
	public String loginUser(UserLoginDto dto)
	{
		authenticationManager.authenticate(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));
		
		String token=jwtUtil.generateToken(rep.findByUserName(dto.getUserName()).get());
		
		return token;
	}

	public UserFeignResponse returnUserDetails(Long userId) throws Exception
	{
		UserRegistration uf= rep.findById(userId).orElseThrow(()-> new Exception("NO USER FOUND"));
		
		UserFeignResponse ufr = new UserFeignResponse();
		ufr.setDesignation(uf.getDesignation().toString());
		ufr.setEmailId(uf.getEmail());
		ufr.setUserId(uf.getId());
		ufr.setUserName(uf.getUserName());
		
		return ufr;
	}
	
	public EmployeeManagement validateEmployee(UserRegDto dto)
	{
		Optional<EmployeeManagement> em= emr.findById(dto.getEmployeeId());
		
		if(!em.isPresent())
		{
			throw new RuntimeException("Employee not found");
		}
		else
		{
			EmployeeManagement employee= em.get();
			
			if(employee.getAccountActivated())
			{
				throw new RuntimeException("Employee account is already activated");
			}
			else if(!employee.getEmail().equals(dto.getEmail()))
			{
				throw new RuntimeException("Email does not match with employee record");
			}
			
			if(rep.findByEmail(dto.getEmail()).isPresent())
			{
				throw new UserAlreadyRegisteredException("User with this email is already registered");
			}
			
			employee.setAccountActivated(true);
			return employee;
		}
	}
	
	public UserRegistration createUser(UserRegDto dto, EmployeeManagement employee)
	{
		UserRegistration reg= new UserRegistration();
		reg.setEmployeeId(employee.getId());
		reg.setEmail(dto.getEmail());
		reg.setUserName(dto.getUserName());
		reg.setPassword(bpe.encode(dto.getPassword()));
		reg.setRegistrationDate(LocalDateTime.now());
			
		if(employee.getDesignation().equals(Designation.PROJECT_MANAGER))
		{
			reg.setDepartment(employee.getDepartment());
			reg.setDesignation(employee.getDesignation());
			Roles role= rolerep.findByRoleName("ROLE_MANAGER").get();
			reg.getRoles().add(role);	
		}	
		else if(employee.getDesignation().equals(Designation.PROJECT_ADMINISTRATOR))
		{
			reg.setDepartment(employee.getDepartment());
			reg.setDesignation(employee.getDesignation());
			Roles role= rolerep.findByRoleName("ROLE_ADMIN").get();
			reg.getRoles().add(role);	
		}
		else
		{
			reg.setDepartment(employee.getDepartment());
			reg.setDesignation(employee.getDesignation());
			Roles role= rolerep.findByRoleName("ROLE_USER").get();
			reg.getRoles().add(role);
		}
			
		return rep.save(reg);
	 }
	public String createUserProfile(UserRegistration reg, Long employeeId)
	{
		UserProfileDto updto= new UserProfileDto();
		updto.setEmail(reg.getEmail());
		updto.setUserName(reg.getUserName());
		updto.setEmployeeId(employeeId);
		updto.setDepartment(reg.getDesignation().toString());
		updto.setProfileCreatedAt(reg.getRegistrationDate());
		
		String res= userServiceFeign.createUserProfile(updto).getBody();
		
		return res;	
	}
	public String createProjectDetails(UserRegistration reg)
	{
		UserDetailsDto dt= new UserDetailsDto();
		dt.setEmail(reg.getEmail());
		dt.setUserId(reg.getId());
		dt.setUserName(reg.getUserName());
	
		String resp= project.saveUserDetails(dt).getBody();
		
		return resp;	
	}
}