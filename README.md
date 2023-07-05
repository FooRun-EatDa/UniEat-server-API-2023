<h1 style="display: flex; position: relative; align-items: center; margin: 0; padding: 0; font-size: 4em;">
<img src="src/main/resources/images/logo/UNI-EAT(transparent).png" alt="logo" width="90" height="100" style="margin: 5px;">
UNI-EAT API
</h1>
<p>FooRun의 UniEat Application API Module</p>
<h1>0. 프로젝트 소개</h1>
<span>UNI-EAT Application의 기능 서비스 제공을 위한 프로젝트입니다.</span>
<p>notion link: <a>https://www.notion.so/UNI-EAT-5cab4deafc654b24857617e4e2d98417</a></p>
<h1>1. 환경 구성</h1>
<span style="display: inline-flex; align-items: center;"><img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white" alt="Java" style="margin-right: 8px;">8 or 11</span>
<br/>
<span style="display: inline-flex; align-items: center;"><img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white" alt="SpringBoot" style="margin-right: 8px;">2.7.9</span>
<br/>
<img src="https://img.shields.io/badge/AWS-232F3E?style=flat&logo=AmazonAWS&logoColor=white" alt="SpringBoot" style="margin-right: 8px;">
<br/>
<span style="display: inline-flex; align-items: center;"><img src="https://img.shields.io/badge/EC2-FF9900?style=flat&logo=AmazonEC2&logoColor=white" alt="AWS EC2" style="margin-right: 8px;"><img src="https://img.shields.io/badge/Amazon Linux-FCC624?style=flat&logo=Linux&logoColor=black" alt="Amazon Linux" style="margin-right: 8px;"></span>
<br/>
<span style="display: inline-flex; align-items: center;"><img src="https://img.shields.io/badge/RDS-527FFF?style=flat&logo=AmazonRDS&logoColor=white" alt="AWS RDS" style="margin-right: 8px;"><img src="https://img.shields.io/badge/MariaDB-003545?style=flat&logo=MariaDB&logoColor=white" alt="AWS RDS" style="margin-right: 8px;"></span>
<h1>2. 개발 시 특이사항</h1>
<h6>1. DB 연결</h6>
<span>RDS는 외부연결을 허용하지 않고 EC2와만의 연결이 열려있기 때문에, EC2를 경유하여 접속해야 함(connect with SSH 참조)</span>
<br/>
<span>local에서 개발 시, com.jcraft:jsch를 통해 EC2를 경유하여 접속 처리(profile: local 사용)</span>