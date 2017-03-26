# Author : Sharanya Srinivas
# Approximate Entropy

rm(list = ls())

library(ggplot2)
library(segmented)

file = "C:/Users/User/Google Drive/MIT Heart Data/"

hr1 <- as.matrix(read.table(paste(file,"Data/hr_1.txt",sep = ""),sep = "\n",header = FALSE))
hr2 <- as.matrix(read.table(paste(file,"Data/hr_2.txt",sep = ""),sep = "\n",header = FALSE))
hr3 <- as.matrix(read.table(paste(file,"Data/hr_3.txt",sep = ""),sep = "\n",header = FALSE))
hr4 <- as.matrix(read.table(paste(file,"Data/hr_4.txt",sep = ""),sep = "\n",header = FALSE))
N1 <- length(hr1); N2 <- length(hr3)

ApEn <- function(HR)   
{
  # Approximate Entropy,
  # m = 2 ; r = 20% # From Literature 
  r = 0.2
  N = length(HR)
  c_2 <- rep(0,N-1)
  for (i in 1:(N-1))
  {
    c_2[i] <- sum(apply(abs(cbind(HR[1:(N-1)]-HR[i],HR[2:N]-HR[i+1])),1,max)<r)/(N-1)
  }
  C_2 = mean(c_2,na.rm=TRUE)
  c_3 <- rep(0,(N-2))
  for (i in 1:(N-1))
  {
    c_3[i] <- sum(apply(abs(cbind(HR[1:(N-2)]-HR[i],HR[2:(N-1)]-HR[i+1],HR[3:N]-HR[i+2])),1,max)<r)/(N-2)
  }
  C_3 = mean(c_3,na.rm = TRUE)
  AE <- log(C_2/C_3)
  return(AE)
}
AE1 <- ApEn(hr1)
AE2 <- ApEn(hr2)
