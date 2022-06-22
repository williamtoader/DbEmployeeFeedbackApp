# Employee Feedback Application



### Model: User
#### Fields:
- email: Email
- profile: Profile
- roles: Role (?)

### Model: Email
#### Fields:
- email: String
- valid: Boolean

### Model: Profile
#### Fields:
- user: User
- biographyText: String
- fullName: String
- profilePhotoURL: String
- reviews: \[Review\]
##### Transient:
- computeMeanScores(timeDelta: ?):  ScoreOutputDTO

### Model: Review
#### Fields: 
- comment: String
- score1: Double  
- score2: Double  
- score3: Double  
... where score1, score2 will be replaced with the name of the scores

**DTO**: ScoreOutputDTO
Fields:
- score1: Double
- score2: Double
- score3: Double  
  ... where score1, score2 will be replaced with the name of the scores



### Model: AuthenticationDetails
#### Fields:
- user: User
- passwordHash: String
##### Transient:
- validate(plainTextPassword: String): Boolean


## Services

### LoginService:

#### Methods:
- login(input: LoginInputDTO): (refreshToken: String)
- resetTokens(user: User)

### OnboardingService
This service does the E-mail validation

### AccessControlService
- Checks E-mail is confirmed
- Retrieves user permissions
- Provides context to other services (Service-Service and Controller-Service access is done only through context)



