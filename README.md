# Sistemas Operacionais - Trabalho 1

### 1. Definição da Máquina Virtual (MV)
Nossa máquina virtual (MV) tem CPU e Memória.
### 1.1 Memória
Considere a memória como um array contíguo de posições de memória. Cada posição de memória são 4 bytes. A memória tem
* 1024 posições.
* tamMemoria = 1024
* array mem[tamMemoria] of posicaoDeMemoria // adotamos 32 bits
### 1.2 CPU - versão 3
O processador possui os seguintes registradores:
* um contador de instruções (PC)
* oito registradores, 0 a 7
O conjunto de instruções é apresentado na tabela a seguir, adaptado de [1]

### 1.3 Programas
Construa os seguintes programas para a nossa MV, no mínimo os que seguem:
* (fibonacci10) O programa escreve em posições sabidas de memórias os 10 números da sequência
de fibonacci. Ou seja, ao final do programa a memória tem estes 10 números em
posições convencionadas no programa.
* (fibonaccin) O programa lê um valor de uma determinada posição (carregada no inicio), \
 se o número for menor que zero coloca -1 no início da posição de memória para \
saída; \
 Se for maior que zero este é o número de valores da sequencia de fibonacci a serem escritos em sequencia a partir de uma
posição de memoria;
* (fatorial) Dado um inteiro em alguma posição de memória, \
 se for negativo armazena -1 na saída; \
 se for positivo responde o fatorial do número na saída.
* (bubblesort) para um N definido (5 por exemplo) \
 o programa ordena um vetor de N números em alguma posição de memória; \
 ordena usando bubble sort \
 loop ate que nao swap nada \
 passando pelos N valores \
 faz swap de vizinhos se da esquerda maior que da direita

### 1.4 Shell
Construa um shell, ou seja, um terminal interativo que:
* oferece um prompt ao usuário
* o usuário pode solicitar a execução de um programa
* a partir disso
  * o programa é carregado
  * executado
  * ao acabar retorna controle para o shell
* para armazenar o programa, pode-se adotar
  * um arquivo por programa, ou mesmo
  * que existe uma estrutura em memória com os programas que são então carregados para a memória da VM.
### 2. PROBLEMA
Implemente: a MV descrita acima; o Shell; e rotinas necessárias. \
Defina como acontece a carga do programa em memória e o início da execução. \
Execute os programas P1 a P4 na MV. \
Instrumente para ver o andamento do processamento na MV.

# Elementos Importantes Definidos
### 1. ESTRUTURA DE ARMAZENAMENTO
Cada palavra da memória é um objeto com os atributos necessários para codificar:
* OPCODE
* REG 1..8
* REG 1..8
* PARAMETRO K OU A

### 2. PROGRAMA PODE SER
Escrito em .txt \
ou \
Codificado em Java como a criação de um vetor de objetos "Palavra"

### 3. NAO MANIPULAREMOS O NIVEL DE BIT, MAS A NIVEL DE PALAVRA
Assim operações lógicas a nível de bit não são mais necessárias: \
 AND, OR, NOT, … SHIFT ….
 
### 4. A MAQUINA VIRTUAL É UM GRANDE LOOP,
   EM CADA ITERACAO \
       CARREGA-SE A INSTRUCAO APONTADA POR PC \
           (A PALAVRA NA POSICAO DA MEMORIA INDEXADA POR PC) \
       EXECUTA-SE A INSTRUCAO CONFORME SEUS DADOS, \
           MODIFICANDO REGISTRADORES E POSICOES DE MEMORIA
       QUANDO ENCONTRAR STOP O PROGRAMA PÁRA. \
       
 A OPERACAO DE STORE EM UMA POSICAO DE MEMORIA ADOTA A \
 DEFINICAO QUE ARMAZENA O VALOR NA EM “PARAMETRO" DO OBJETO \
 “PALAVRA” QUE ESTA NAQUELA POSICAO DA MEMORIA. \
 ADOTA-SE UM OPCODE NOVO, CHAMADO DE “DADO”, QUE INDICA QUE NAQUELA \
 POSICAO TEMOS UM DADO E NAO UMA INSTRUCAO.

 ### 5. UM CARREGADOR, QUANDO INVOCADO TENDO COMO PARAMETRO UM PROGRAMA,
   LÊ O PROGRAMA SEQUENCIALMENTE E CARREGA A PARTIR DA POSICAO 0 DA MEMÓRIA
 
 ### 6. UM SHELL É UM PROGRAMA QUE ESPERA UMA ENTRADA DO USUARIO
   (QUE É UM NOME DE PROGRAMA) \
 INVOCA O CARREGADOR, PASSANDO O NOME DO PROGRAMA COMO PARAMETRO \
 E LIBERA A MAQUINA VIRTUAL (ITEM 4) PARA EXECUTAR A PARTIR DA POSICAO 0
