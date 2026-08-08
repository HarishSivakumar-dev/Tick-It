local key= KEYS[1]

local time= tonumber(ARGV[1])
local window= tonumber(ARGV[2])
local member= ARGV[3]

redis.call('ZREMRANGEBYSCORE', key, 0, time - window)
local count= redis.call('ZCARD', key)

if count>9 then
    return 0
else
    redis.call('ZADD', key,time,member)
    redis.call('EXPIRE', key, 600)
    return 1
end


