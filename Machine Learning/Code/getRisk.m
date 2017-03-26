function Risk = getRisk(alpha1,alpha2,Apen, age)
%Finding the value of a, mu and std_dev
[a,~,mu]= get_a_mu(age);
%Caluclating different d
l=[alpha1 alpha1 Apen];
d=l-mu;
%Calculating x= dot(a, d)
x=a.*d;
%Calculating sigmoid
R=1.0 ./ ( 1.0 + exp(-(x)));
%Final Risk Calculation
Risk=prod(R);
end