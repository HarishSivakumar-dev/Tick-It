local key= KEYS[1]

local time= tonumber(ARGV[1])
local window= tonumber(ARGV[2])
local member= ARGV[3]
local limit= tonumber(ARGV[4])

local count= redis.call('ZCARD', key)

if(count==0) then
    redis.call('ZADD', key,time,member)
    redis.call('EXPIRE', key, window)
    return 1
else
	redis.call('ZREMRANGEBYSCORE', key, 0, time - window)
	local count= redis.call('ZCARD', key)+1

	if count>limit then
    	return 0
	else
    	redis.call('ZADD', key,time,member)
    	redis.call('EXPIRE', key, window)
    	return 1
	end
end


