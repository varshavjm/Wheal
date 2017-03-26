rm(list = ls())

library(glmnet)
library(quantreg)
library(genlasso)

file = "C:/Users/User/Google Drive/MIT Heart Data/"

hr_1 <- as.matrix(read.table(paste(file,"Data/hr_2.txt",sep = ""),sep = "\n",header = FALSE))
n <- length(hr_1)

Time <- matrix(1:n,n,1) #Add Time as a predictor

# Sinusoids
s <- 5*2*60; # season = 5 min
h <- s/2 # Harnomics
h_mat <- outer(rep(1,n),1:h)
time_mat <- outer(1:n,rep(1,h))
sines <- sin(2*pi*time_mat*h_mat/s)
cosines <- cos(2*pi*time_mat*h_mat/s)

Data <- data.frame(hr_1)
sinusoids <- data.frame(sines,cosines)

x <- as.matrix(sinusoids)
y <- as.matrix(Data)

# LASSO
  lasso_fit <- cv.glmnet(x,y)
  lasso_yhat <- predict(lasso_fit,x)
  lasso_coef <- as.numeric(coef(lasso_fit))
#--------------------------------------
# Adaptive LASSO
  temp_fit <- cv.glmnet(x,y)
  alasso_fit <- cv.glmnet(x,y,penalty.factor = abs(1/as.numeric(coef(temp_fit,s = temp_fit$lambda.min)+(1/n))))
  alasso_yhat <- predict(alasso_fit,x)
  alasso_coef <- as.numeric(coef(alasso_fit))
#--------------------------------------
# Adaptive LAD LASSO
  tempcoef <- coef(rq(y~x,tau = .5,method = "lasso",lambda = 1))
  alad_lam = log(length(y))/abs(tempcoef); alad_lam[1]=0
  alad_fit <- rq(y~x,.5,method = "lasso",lambda = alad_lam)
  alad_yhat <- predict(alad_fit,data.frame(x))
  alad_coef <- as.numeric(coef(alad_fit))
# ---------------------------------------
# Fused LASSO
  fused_fit_2 <- trendfilter(y,ord = 2)
  fused_fit_3 <- trendfilter(y,ord = 3)
  fused_yhat_2 <- fused_fit_2$beta[,2000]
  fused_yhat_3 <- fused_fit_3$beta[,2000]
#----------------------------------------
# Plots
  plot(y)
  lines(lasso_yhat,col="blue",lwd=4)
  lines(alasso_yhat,col="green",lwd=4)
  lines(alad_yhat,col="dark green",lwd=4)
  lines(fused_fit_2$beta[,2000],col="red",lwd=4)
  lines(fused_fit_3$beta[,2000],col="red",lwd=4)
#---------------------------------------
