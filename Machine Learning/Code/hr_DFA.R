# Author : Sharanya Srinivas
# Detrended Functional Analysis

rm(list = ls())

library(ggplot2)
library(segmented)

file = "C:/Users/User/Google Drive/MIT Heart Data/"

hr1 <- as.matrix(read.table(paste(file,"Data/hr_1.txt",sep = ""),sep = "\n",header = FALSE))
hr2 <- as.matrix(read.table(paste(file,"Data/hr_2.txt",sep = ""),sep = "\n",header = FALSE))
# hr3 <- as.matrix(read.table(paste(file,"Data/hr_3.txt",sep = ""),sep = "\n",header = FALSE))
# hr4 <- as.matrix(read.table(paste(file,"Data/hr_4.txt",sep = ""),sep = "\n",header = FALSE))
N1 <- length(hr1); N2 <- length(hr3)

DFA <- function(d)   
{
  # DFA
  N <- length(d)
  
  # Converting into Random Walk
  X = cumsum(d) - mean(d)
  # Window
  f = numeric(N)
  for (n in 1:N)
  { sum = 0
  x <- 1:n
  for (m in 1:floor(N/n))
  {
    y <- X[((m-1)*n+1) : (m*n) ]
    lm_obj <- lm(y~x)
    Y <- predict(lm_obj)
    sum = sum + sum((y - Y)^2)
  }
  f[n] <- sqrt(sum/N);
  }
  return(f)
}

f1 <- log10(DFA(hr1)); f2 <- log10(DFA(hr2))
# f4 <- log10(DFA(hr4)); f3 <- log10(DFA(hr3))


Time1 <- log10(1:N1); index <- which(Time1<=0.5); Time1<-Time1[-index]; 
f1 <- f1[-index]; f2 <- f2[-index];
f1_seg_fit <- segmented(lm(f1~Time1),seg.Z = ~Time1)
f2_seg_fit <- segmented(lm(f2~Time1),seg.Z = ~Time1)
f1_seg <- predict(f1_seg_fit)
f2_seg <- predict(f2_seg_fit)

lmfit_1 <- data.frame(Time1,f1,f2,f1_seg,f2_seg)

# Time2 <- log10(1:N2); index <- which(Time2<=0.5); Time2<-Time2[-index]; 
# f3 <- f3[-index]; f4 <- f4[-index];
# f3_seg <- predict(segmented(lm(f3~Time2),seg.Z = ~Time2))
# f4_seg <- predict(segmented(lm(f4~Time2),seg.Z = ~Time2))
# lmfit_2 <- data.frame(Time2,f3,f4,f3_seg,f4_seg)

p1 <- ggplot(lmfit_1,aes(x=Time1,y=f))
p1 + geom_point(aes(y=f1,color="red")) + geom_point(aes(y=f2,color="blue")) +
geom_line(aes(y=f1_seg,color="red")) + geom_line(aes(y=f2_seg,color="blue"))

# p2 <- ggplot(lmfit_2,aes(x=Time2,y=f))
# p2 + geom_point(aes(y=f3,color="red")) + geom_point(aes(y=f4,color="blue")) +
# geom_line(aes(y=f3_seg,color="red")) + geom_line(aes(y=f4_seg,color="blue"))

s1 <- slope(f1_seg_fit)$Time1
alpha1_1 <- s1[1]
alpha2_1 <- s1[2]

s2 <- slope(f2_seg_fit)$Time1
alpha1_2 <- s2[1]
alpha2_2 <- s2[2]
