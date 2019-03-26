
<?php header('Access-Control-Allow-Origin:*'); ?>
<?php 
if ($_SERVER['REQUEST_METHOD'] === 'POST')
{
$dirname="C:\\xampp\htdocs\CryptoSystem\Test_Images"." ";
$filename=$_POST['file'];
// echo $dirname;
// echo $filename;
$command="java -jar C:\Users\Yagzan\Documents\Final_year_Project\CMRGCryptoSystem\dist\CMRGCryptoSystem.jar"." ".$dirname." ".$filename;
// echo $command;
if(shell_exec($command))
	$response = array("status"=>"200","description"=>"success");
else
	$response = array("status"=>"500","description"=>"Not Executed");
 
}
else
$response = "Welcome Hacker!";
echo json_encode($response);

 ?>

