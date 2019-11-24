## Exporting a lambda function from IntelliJ

### Maven

After each pull/push, make sure to reimport `pom.xml` as a Maven project!!!

### Lambda

To publish a lambda function, open the IntelliJ AWS explorer window and right click Lambda and select `create new aws lambda`

Afterwards, just specify the path to the lambda function handler and give the lambda function a name and IAM role.

Note that the lambda function name/class name should match for ease of redeploying via build scripts! E.g. a lambda function handler GetVideoHandler should correspond to a lambda function named GetVideo (note the lack of 'Handler' suffix)
