function [a,std_dev,mu]= get_a_mu(age)

if strcmp(age,'Young')
    mu=[1.20 0.98 1.12];
    std_dev=[0.16 0.19 0.12];
    
elseif strcmp(age,'Middle-aged')
    mu=[1.24 1.06 0.98];
    std_dev=[0.14 0.08 0.13];
else
    mu=[1.15 1.12 0.91];
    std_dev=[0.16 0.10 0.17];
    
end

 a = geta(std_dev);


end