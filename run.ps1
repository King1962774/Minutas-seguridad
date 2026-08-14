$mvn = "$env:TEMP\maven\apache-maven-3.9.6\bin\mvn.cmd"
if (!(Test-Path $mvn)) {
    Write-Host "Descargando Maven..." -ForegroundColor Cyan
    $url = "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"
    $output = "$env:TEMP\maven.zip"
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    Invoke-WebRequest -Uri $url -OutFile $output
    Expand-Archive -LiteralPath $output -DestinationPath "$env:TEMP\maven" -Force
}
& $mvn javafx:run
