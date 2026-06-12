# API Reference

Admin3 HTTP API is based on Resource & Action design, which is a superset of REST API. Action is not limited to CRUD operations.

## URI Specification

URI structure https://[host]:[port]/{service name}]/v{version number}/{resource}

**Requirements**

* Do not use uppercase

* Use hyphens - instead of underscores _

* Parameter lists must be encoded

* Nouns in the URI represent resource collections and use plural forms

## Resources

REST API is a "collection" of individually addressable "resources" (the "nouns" in the API). Resources are referenced by resource names and controlled by a set of "methods" (also called "verbs" or "operations").

API standard methods (also called "REST methods") include List, Get, Create, Update, and Delete. API designers can also use "custom methods" (also called "custom verbs" or "custom operations") to implement functionality that cannot be easily mapped to standard methods.

## Methods

A key feature of resource-oriented APIs is the emphasis on resources (data model) over the methods performed on resources (functionality). Typical resource-oriented APIs expose a large number of resources using a small number of methods. Methods can be standard methods or custom methods. For this guide, the standard methods are: List, Get, Create, Update, and Delete.

If an API function can naturally map to a standard method, that method should be used in the API design. For functionality that does not naturally map to a standard method, custom methods can be used.

### Standard Methods

| Standard Method   | HTTP Mapping                                        | HTTP Request Body | HTTP Response Body |
|--------|-----------------------------------------------|----------|----------|
| List   | `GET <collection URL>`                         | None        | Resource* list    |
| Get    | `GET <collection URL>/<collection_id>`          | None        | Resource*      |
| Create | `POST <collection URL>`                         | Resource       | Resource*      |
| Update | `PUT or PATCH <resource URL>/<collection_id>` | Resource       | Resource*      |
| Delete | `DELETE <resource URL>/<collection_id>`         | N/A      |          |

### Custom Methods

Custom methods refer to API methods other than the 5 standard methods. These methods should only be used for functionality that is not easily expressed by standard methods. Typically, API designers should prioritize using standard methods over custom methods. Standard methods have simpler and more clearly defined semantics that most developers are familiar with, making them easier to use and less error-prone.

For custom methods, the following general HTTP mapping should be used:

```
https://service.name/v1/some/resource/name:customVerb
```

The following are examples of custom methods:

| Method Name     | Custom Verb     | HTTP Verb | Remarks                            |
|----------|-----------|--------|-------------------------------|
| Cancel   | :cancel   | POST   | Cancel an unfinished operation                    |
| BatchGET | :batchGet | GET    | Batch get multiple resources                      |
| Move     | :move     | POST   | Move a resource from one parent to another              |
| Search   | :search   | GET    | Alternative to List, used for data that does not fit List semantics |
| Undelete | :undelete | POST   | Restore a previously deleted resource |

Note: The above cases refer to API names; HTTP/JSON URI suffixes use :lowerCamelCase.

## Requests

### Authentication

**Request login interface to obtain Token**

```
curl -X 'POST' \
'http://localhost:8080/login' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"username": "admin",
"password": "123456"
}'
```

**Add Token to request header**

Token rule: Authorization: Bearer + {Token}

```
curl -X 'GET' \
  'http://localhost:8080/admin3/users?size=20&page=1&state=NORMAL' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer c023825e63104d079766b66b7e950da9'
```

### Request Parameters

Request parameters can be placed in the Request's headers, parameters (query string), or body (GET requests have no body).

A few special Parameters request parameters:

* `page`  The page to retrieve, default is 1
* `size`  The number of items per page, default is 20
* `sort`  One or more properties to sort the result by, using the following format: property1, property2 (,asc|desc) – for example, ?sort=name&sort=email,asc

## Response

### HTTP Status Codes

| Status Code | Description                                    |
|-----|---------------------------------------|
| 200 | Ok, data successfully returned by the server                        |
| 201 | Created, user created or modified data successfully                  |
| 400 | Bad Request, user request has errors, such as parameter validation failure, etc.     |
| 401 | Unauthorized, user not authorized (not logged in, wrong password, account disabled) |
| 403 | Forbidden, user is prohibited from accessing the resource                  |
| 404 | Not Found, requested record does not exist                   |
| 500 | Internal Server Error, system-level error, unrecoverable exception |

### Response Body

The body of a normal response should directly be the data, without unnecessary wrapping. Only when an error occurs should it be wrapped. Error example:

```json
{
  "code": 1002,
  "message": "Invalid parameter",
  "data": {},
  "errors": [{"username":"Cannot be empty"}]
}
```

**Description**

| Property      | Type     | Required | Description   |
|---------|--------|------|------|
| code    | number | Yes    | Business status code |
| message | string | Yes    | Business message |
| data    | any    | No    | Exception data |
| errors  | array  | No    | Error list |

 **Business Status Codes**

| Status Code  | Description      |
|------|---------|
| 1001 | Failed      |
| 1002 | Invalid parameter    |
| 1003 | Record does not exist   |
| 1004 | Unauthorized     |
| 1005 | Forbidden    |
| -1   | Internal server error |

For paginated interfaces, the total count may need to be returned, with the following structure:
```json lines
{
  "list": [
    {
      "id": 1
    },
    {
      "id": 2
    }
  ],
  "total": 2
}
```

## Examples

Taking roles and users as examples:

* Get a single role `GET /roles/{roleId}`

* Get role list `GET /roles`

* Add role `POST /roles`

* Update role `PUT /roles/{roleId}`

* Delete role `DELETE /roles/{roleId}`

* Get all users under a role `GET /roles/{roleId}/users`

* Disable user POST `/users/{userId}:disable`

* Enable user POST `/users/{userId}:enable`
