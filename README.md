KyobongBooks API Document
===
교봉문고의 도서 관리 시스템을 위한 API 문서입니다. 카테고리 관리 및 카테고리별 도서 관리 기능을 제공합니다.

Category
===
도서의 카테고리 정보를 검색 및 추가, 수정, 삭제하는 API 입니다.

* [카테고리 검색](#카테고리-검색)
* [카테고리 추가](#카테고리-추가)
* [카테고리 수정](#카테고리-수정)
* [카테고리 삭제](#카테고리-삭제)


# 카테고리 검색

카테고리의 이름, 설명으로 카테고리를 검색합니다.

**Endpoint** : `/category/list`

**Method** : `POST`


## Request

**Parameters**

* `name` : string, 카테고리의 이름
* `description` : string, 카테고리의 설명


**Example Request**

```json
{
  "name": "학"
}
```


## Response

**Parameters**

* Array : 검색된 카테고리의 리스트 (Array of : object `category`)
    * `category` : object
        * `name` : string, 카테고리의 이름
        * `description` : string, 카테고리의 설명


**Example Response**

```json
[
  {
    "name": "과학",
    "description": ""
  },
  {
    "name": "문학",
    "description": ""
  },
  {
    "name": "인문학",
    "description": ""
  }
]
```


# 카테고리 추가

신규 카테고리를 추가합니다.

**Endpoint** : `/category/add`

**Method** : `POST`

## Request

**Parameters**

* Array : object, 저장할 카테고리 리스트 (Array of : object `category`)
    * `category` : object
        * `name` : string, 카테고리의 이름
        * `description` : string, 카테고리의 설명


**Example Request**

```json
[
  {
    "name": "역사",
    "description": "역사 관련 도서"
  },
  {
    "name": "IT",
    "description": "IT 관련 도서"
  }
]
```

## Response

**Parameters**

* `dataset` : array, 저장된 카테고리 객체의 리스트 (Array of : object `category`)
    * `category` : object
        * `name` : string, 카테고리의 이름
        * `description` : string, 카테고리의 설명
* `errors` : array, 저장 중 발생한 오류 객체 리스트 (Array of : object `error`)
    * `error` : object
        * `data` : 오류가 발생된 카테고리 객체
        * `message` : 오류 메시지


**Example Response**

```json
{
  "dataset": [
    {
      "name": "역사",
      "description": "역사 관련 도서"
    }
  ],
  "errors": [
    {
      "data": {
        "name": "IT",
        "description": "IT 관련 도서"
      },
      "message": "카테고리명이 이미 존재합니다."
    }
  ]
}
```


# 카테고리 수정

카테고리의 설명 정보를 수정합니다.

**Endpoint** : `/category/update`

**Method** : `POST`


## Request

**Parameters**

* Array : 수정할 카테고리 리스트 (Array of : object `category`)
    * `category` : object
        * `name` : string, 카테고리의 이름
        * `description` : string, 카테고리의 설명


**Example Request**

```json
[
  {
    "name": "역사",
    "description": "역사 관련 도서"
  },
  {
    "name": "여행",
    "description": "여행 관련 도서"
  }
]
```


## Response

**Parameters**

* `dataset` : array, 저장된 카테고리 객체의 리스트 (Array of : object `category`)
    * `category` : object
        * `name` : string, 카테고리의 이름
        * `description` : string, 카테고리의 설명
* `errors` : array, 저장 중 발생한 오류 객체 리스트 (Array of : object `error`)
    * `error` : object
        * `data` : 오류가 발생된 카테고리 객체
        * `message` : 오류 메시지


**Example Response**

```json
{
  "dataset": [
    {
      "name": "역사",
      "description": "역사 관련 도서"
    }
  ],
  "errors": [
    {
      "data": {
        "name": "여행",
        "description": "여행 관련 도서"
      },
      "message": "카테고리가 존재하지 않습니다."
    }
  ]
}
```


# 카테고리 삭제

카테고리를 삭제합니다.

**Endpoint** : `/category/delete`

**Method** : `POST`


## Request

**Parameters**

* Array : 삭제할 카테고리의 이름 리스트 (Array of : String)


**Example Request**

```json
[
  "역사", "경제경영"
]
```


## Response

**Parameters**

* `deleteCount` : integer, 삭제된 도서의 개수
* `errors` : array, 저장 중 발생한 오류 객체 리스트 (Array of : object `error`)
    * `error` : object
        * `data` : 오류가 발생된 카테고리 이름
        * `message` : 오류 메시지

**Example Response**

```json
{
  "deleteCount": 1,
  "errors": [
    {
      "data": "경제경영",
      "message": "사용중인 카테고리는 삭제할 수 없습니다."
    }
  ]
}
```

---


Book
===
도서의 정보를 검색 및 추가, 수정, 삭제하는 API 입니다.

* [도서 검색](#도서-검색)
* [도서 추가](#도서-추가)
* [도서 수정](#도서-수정)
* [도서 삭제](#도서-삭제)


# 도서 검색

제목, 지은이, 카테고리명 등으로 도서를 검색합니다.

**Endpoint** : `/book/list`

**Method** : `POST`


## Request

**Parameters**

* `title` : string, 검색할 도서의 제목
* `author` : string, 검색할 도서의 지은이
* `rentalYN` : string, 대여가능 여부 (Y/N)
* `status` : string, 도서의 상태
* `categoryNames` : array, 검색할 카테고리명 리스트 (Array of : string)


**Example Request**

```json
{
  "author": "권태영",
  "categoryNames": [
    "경제경영", "IT"
  ]
}
```


## Response

**Parameters**

* Array : 검색된 도서 객체의 리스트 (Array of : object `book`)
    * `book` : object
        * `id` : long, 도서의 ID
        * `title` : string, 도서의 제목
        * `author` : string, 도서의 지은이
        * `rentalYN` : string, 대여가능 여부 (Y/N)
        * `status` : string, 도서의 상태
        * `categoryNames` : array, 도서의 카테고리명 리스트 (Array of : string)


**Example Response**

```json
[
  {
    "id": 4,
    "title": "트랜드 코리아 2322",
    "author": "권태영",
    "rentalYN": "Y",
    "status": "정상",
    "categoryNames": [
      "경제경영"
    ]
  },
  {
    "id": 13,
    "title": "Skye가 알려주는 피부 채색의 비결",
    "author": "권태영",
    "rentalYN": "Y",
    "status": "정상",
    "categoryNames": [
      "IT"
    ]
  }
]
```


# 도서 저장

신규 도서를 추가합니다.

**Endpoint** : `/book/add`

**Method** : `POST`


## Request

**Parameters**

* Array : object, 저장할 도서 리스트 (Array of : object `book`)
    * `book` : object
        * `title` : string, 도서의 제목
        * `author` : string, 도서의 지은이
        * `rentalYN` : string, 대여가능 여부 (Y/N)
        * `status` : string, 도서의 상태
        * `categoryNames` (required) : array, 도서의 카테고리명 리스트


**Example Request**

```json
[
  {
    "title": "너에게 해주지 못한 말들",
    "author": "권태영",
    "rentalYN": "Y",
    "status": "정상",
    "categoryNames": ["문학", "인문학"]
  },
  {
    "title": "트랜드 코리아 2322",
    "author": "권태영",
    "categoryNames": []
  }
]
```


## Response

**Parameters**

* `dataset` : array, 저장된 도서 객체의 리스트 (Array of : object `book`)
    * `book` : object
        * `id` : long, 도서의 ID
        * `title` : string, 도서의 제목
        * `author` : string, 도서의 지은이
        * `rentalYN` : string, 대여가능 여부 (Y/N)
        * `status` : string, 도서의 상태
        * `categoryNames` : array, 도서의 카테고리명 리스트 (Array of : string)
* `errors` : array, 저장 중 발생한 오류 객체 리스트 (Array of : object `error`)
    * `error` : object
        * `data` : 오류가 발생된 도서 객체
        * `message` : 오류 메시지


**Example Response**

```json
{
  "dataset": [
    {
      "id": 22,
      "title": "너에게 해주지 못한 말들",
      "author": "권태영",
      "rentalYN": "Y",
      "status": "정상",
      "categoryNames": [
        "문학",
        "인문학"
      ]
    }
  ],
  "errors": [
    {
      "data": {
        "id": null,
        "title": "트랜드 코리아 2322",
        "author": "권태영",
        "rentalYN": null,
        "status": null,
        "categoryNames": []
      },
      "message": "카테고리 입력은 필수입니다."
    }
  ]
}
```


# 도서 수정

도서의 정보를 수정합니다.

**Endpoint** : `/book/update`

**Method** : `POST`


## Request

**Parameters**

* Array : 수정할 도서 리스트 (Array of : object `book`)
    * `book` : object
        * `id` (required) : string, 수정할 도서의 ID
        * `title` : string, 수정할 제목
        * `author` : string, 수정할 지은이
        * `rentalYN` : string, 대여가능 여부 (Y/N)
        * `status` : string, 도서의 상태
        * `categoryNames` : array, 수정할 카테고리명 리스트 (Array of : string)


**Example Request**

```json
[
  {
    "id": 1,
    "title": "너에게 해주지 못한 말들 2"
  },
  {
    "id": 2,
    "categoryNames": ["여행"]
  }
]
```


## Response

**Parameters**

* `dataset` : array, 저장된 도서 객체의 리스트 (Array of : object `book`)
    * `book` : object
        * `id` : long, 도서의 ID
        * `title` : string, 도서의 제목
        * `author` : string, 도서의 지은이
        * `rentalYN` : string, 대여가능 여부 (Y/N)
        * `status` : string, 도서의 상태
        * `categoryNames` : array, 도서의 카테고리명 리스트 (Array of : string)
* `errors` : array, 저장 중 발생한 오류 객체 리스트 (Array of : object `error`)
    * `error` : object
        * `data` : 오류가 발생된 도서 객체
        * `message` : 오류 메시지


**Example Response**

```json
{
  "dataset": [
    {
      "id": 1,
      "title": "너에게 해주지 못한 말들 2",
      "author": "권태영",
      "rentalYN": "Y",
      "status": "정상",
      "categoryNames": [
        "문학"
      ]
    }
  ],
  "errors": [
    {
      "data": {
        "id": 2,
        "title": null,
        "author": null,
        "rentalYN": "Y",
        "status": null,
        "categoryNames": [
          "여행"
        ]
      },
      "message": "카테고리명이 존재하지 않습니다. (카테고리명 : 여행)"
    }
  ]
}
```


# 도서 삭제

도서를 삭제합니다.

**Endpoint** : `/book/delete`

**Method** : `POST`


## Request

**Parameters**

* Array : 삭제할 도서의 ID 리스트 (Array of : long)


**Example Request**

```json
[
  1, 2, 3, 9999
]
```


## Response

**Parameters**

* `deleteCount` : integer, 삭제된 도서의 개수
* `errors` : array, 저장 중 발생한 오류 객체 리스트 (Array of : object `error`)
    * `error` : object
        * `data` : 오류가 발생된 도서 ID
        * `message` : 오류 메시지

**Example Response**

```json
{
  "deleteCount": 3,
  "errors": []
}
```
