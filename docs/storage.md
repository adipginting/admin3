# Object Storage

Admin3 has implemented unified encapsulation for all S3-compliant object storage solutions such as Alibaba Cloud OSS, Huawei Cloud OBS, and MinIO, supporting the simultaneous use of multiple object storage configurations.

## Configuration

1. Add object storage in the `System Management > Object Storage` page

   > If the access domain is not filled in, the vendor address of the object storage will be returned (non-proxy mode) <br>
   > The proxy path is `{{domain}}/admin3/storage/fetch/`, where {{domain}} is configured according to the actual external address <br>
   > The storage directory defaults to `files` if not configured <br>
   > Supports reading system environment variables and JDK configurations as configuration parameters, using the syntax {{VARIABLE_NAME}}

   ![](image/storage.png)

2. Set as default storage location

   > The upload interface can specify a storageId to select the target storage location; multiple object storages can be used simultaneously without affecting each other.

   ![img.png](image/mark-as-default.png)

## File Upload Interface

POST /storage/upload

Content-Type: multipart/form-data

Form Parameters

| Name        | Description                 |
|-----------|--------------------|
| files     | Multiple File files      |
| storageId | Object storage identifier, specifies the upload location, optional, defaults to default location if not filled |

Response

```json lines
[
  {
    "url": "http://xxx"
  },
  {
    "url": "http://xxx"
  }
]
```

## Fetch File

POST /storage/fetch/{key:.+}

Response Headers

Content-Type: application/octet-stream

## Download File

POST /storage/download/{key:.+}

Response Headers

Content-Type: application/octet-stream

Content-Disposition: "attachment; filename=\"" + file.getFilename() + "\""
