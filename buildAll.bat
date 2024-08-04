@echo off
cls
echo ###############################
echo ### Compilando srvDiscovery ###
echo ###############################
cd srvDiscovery
rmdir /s /q target
call mvn clean install -U
pause
cls
echo #############################
echo ### Gerando imagem Docker ###
echo ###      srvDiscovery     ###
echo #############################
call docker image build -t srv-discovery .
pause
echo ###################################
echo ### Fim Compilacao srvDiscovery ###
cd ..

cls
echo #############################
echo ### Compilando srvGateway ###
echo #############################
cd srvGateway
rmdir /s /q target
call mvn clean install -U
pause
cls
echo #############################
echo ### Gerando imagem Docker ###
echo ###       srvGateway      ###
echo #############################
call docker image build -t srv-gateway .
pause
echo #################################
echo ### Fim Compilacao srvGateway ###
cd ..

cls
echo #############################
echo ### Compilando srvCliente ###
echo #############################
cd srvCliente
rmdir /s /q target
call mvn clean install -U
pause
cls
echo #############################
echo ### Gerando imagem Docker ###
echo ###       srvCliente      ###
echo #############################
call docker image build -t srv-cliente .
pause
echo ### Fim Compilacao srvCliente ###
cd ..

cls
echo ############################
echo ### Compilando srvCartao ###
echo ############################
cd srvCartao
rmdir /s /q target
call mvn clean install -U
pause
cls
echo #############################
echo ### Gerando imagem Docker ###
echo ###       srvCartao       ###
echo #############################
call docker image build -t srv-cartao .
pause
echo ### Fim Compilacao srvCartao ###
cd ..


cls
echo ###############################
echo ### Compilando srvPagamento ###
echo ###############################
cd srvPagamento
rmdir /s /q target
call mvn clean install -U
pause
cls
echo #############################
echo ### Gerando imagem Docker ###
echo ###     srvPagamento      ###
echo #############################
call docker image build -t srv-pagamento .
pause
echo ### Fim Compilacao srvPagamento ###
cd ..

cls
echo ###############################
echo ### Compilando srvAutenticacao ###
echo ###############################
cd srvAutenticacao
rmdir /s /q target
call mvn clean install -U
pause
cls
echo #############################
echo ### Gerando imagem Docker ###
echo ###     srvAutenticacao      ###
echo #############################
call docker image build -t srv-autenticacao .
pause
echo ### Fim Compilacao srvAutenticacao ###
cd ..
