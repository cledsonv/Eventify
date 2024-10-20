
# Eventify

## Descrição do Projeto

O **Eventify** é uma plataforma de Gerenciamento de Eventos que permite que organizadores criem e gerenciem eventos, enquanto os usuários podem visualizar, inscrever-se e gerenciar sua participação. O sistema é projetado para atender diferentes tipos de usuários, com funcionalidades específicas para organizadores e participantes, proporcionando uma experiência eficiente na organização e participação de eventos.

## Principais Funcionalidades

### Gestão de Usuários (UserController)
- **Registro e Login de Usuários**: Facilita o registro e autenticação de novos usuários no sistema.
- **Gerenciamento de Papéis**: Define e gerencia os diferentes papéis de usuário, como administrador, organizador e participante, com permissões específicas.

### Gestão de Eventos (EventController)
- **Criação de Eventos**: Permite que organizadores criem eventos detalhados com informações como nome, data, local e descrição.
- **Edição e Exclusão de Eventos**: Disponível apenas para organizadores, possibilitando que eles façam alterações ou removam eventos criados.
- **Listagem e Filtros de Eventos**: Usuários podem visualizar a lista de eventos e utilizar filtros para refinar a busca por data, local, categoria, entre outros.

### Gestão de Inscrições (RegistrationController)
- **Inscrição em Eventos**: Usuários podem se inscrever em eventos do seu interesse.
- **Cancelamento de Inscrições**: Dá aos usuários a opção de cancelar suas inscrições em eventos.
- **Lista de Participantes**: Organizadores podem visualizar e gerenciar a lista de inscritos em seus eventos.

### Funcionalidades Técnicas e Distribuídas
- **Autenticação e Autorização**: Implementação de JWT (JSON Web Tokens) para garantir segurança nas operações e acesso ao sistema.
- **Arquitetura Escalável**: O sistema é projetado para suportar um grande número de usuários e eventos, com distribuição de carga entre servidores para otimizar o desempenho.

## Benefícios e Diferenciais

- **Segurança**: Oferece um ambiente seguro para o gerenciamento de eventos, utilizando autenticação JWT.
- **Escalabilidade**: O sistema é flexível e escalável, preparado para crescer conforme o número de usuários e eventos aumenta.
- **Integração com Plataformas Externas**: Possibilidade de integrar com outras plataformas, proporcionando uma experiência fluida para os usuários.
- **Aplicação Prática de Conceitos Avançados**: Ideal para o aprendizado e aplicação de conceitos de sistemas distribuídos em um ambiente real.

## Instalação

1. **Clone o Repositório**:
   ```bash
   git clone https://github.com/seu-usuario/eventify.git
   ```

2. **Navegue até o diretório do projeto**:
   ```bash
   cd eventify
   ```

3. **Instale as Dependências**:
   ```bash
   npm install
   ```

4. **Configuração de Variáveis de Ambiente**:
   Crie um arquivo `.env` e adicione as seguintes variáveis:
   ```env
   DATABASE_URL=seu_banco_de_dados_url
   JWT_SECRET=sua_chave_jwt_secreta
   PORT=3000
   ```

5. **Execute o Servidor**:
   ```bash
   npm start
   ```

## Uso

Após a instalação e execução, o sistema estará acessível em `http://localhost:3000`. Utilize ferramentas como Postman ou Insomnia para interagir com os endpoints da API.

## Tecnologias Utilizadas

- **Node.js** e **Express.js**: Para a criação do servidor e gerenciamento das rotas.
- **JWT (JSON Web Tokens)**: Para autenticação e autorização de usuários.
- **MongoDB** ou **PostgreSQL**: Banco de dados para armazenamento de informações.
- **Swagger**: Para documentação e testes da API.
- **Docker**: Opcional, para containerização da aplicação.

## Contribuições

Contribuições são bem-vindas! Para contribuir, siga os passos abaixo:

1. Faça um fork do repositório.
2. Crie uma nova branch:
   ```bash
   git checkout -b feature/nova-funcionalidade
   ```

3. Faça commit das suas alterações:
   ```bash
   git commit -m 'Adiciona nova funcionalidade'
   ```

4. Faça push para a branch:
   ```bash
   git push origin feature/nova-funcionalidade
   ```

5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

---

_Este projeto foi criado para facilitar o gerenciamento de eventos, oferecendo uma experiência eficiente e personalizada para organizadores e participantes._
