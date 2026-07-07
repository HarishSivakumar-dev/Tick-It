package com.harish.TickIt.UserService.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService
{
	@Autowired
	private Cloudinary cloud;
	
	@SuppressWarnings("unchecked")
	public String photoUploaded(MultipartFile mpf, Long id)
	{
		try 
		{
		  Map<String,Object> arg= new HashMap<String, Object>();
		  arg.put("public_id","EmployeeId"+id);
		  
		  Map<String, Object> resu=cloud.uploader().upload(mpf.getBytes(), arg);
		  return (String) resu.get("secure_url");
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Cloudinary is down ");
		}
	}
	
	@SuppressWarnings("unchecked")
	public String photoUpdate(MultipartFile mpf, Long id)
	{
		try 
		{
		  Map<String,Object> arg= new HashMap<String, Object>();
		  arg.put("public_id","EmployeeId"+id);
		  arg.put("overwrite", true);
		  
		  Map<String, Object> resu=cloud.uploader().upload(mpf.getBytes(), arg);
		  return (String) resu.get("secure_url");
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Cloudinary is down ");
		}
	}
	
	@SuppressWarnings("unchecked")
	public String photoDelete(Long id)
	{
		try 
		{
			Map<String, Object> mp=cloud.uploader().destroy("EmployeeId"+id, ObjectUtils.emptyMap());
			return (String) mp.get("result");
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Cloudinary is down ");
		}
	}
	
}
