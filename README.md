# Scala Futures and Exceptions

This project provides examples on how to manage futures and exceptions on PlayFramework Actions.

The controller file includes several examples. Those functions that end with "e" have explicit exception management. For example in function `a` and function `ae`.

See the routes file for a list of working endpoints. For those `POST` requests the following payload can be used:

```
{
    "name": "John",
    "age": 20
}
```

Timeouts are added using Play's `Futures` class as described in [Handling time outs](https://www.playframework.com/documentation/2.7.x/ScalaAsync#Handling-time-outs)

## Tests and results

The following code shows calls to all enpoints and their respective results.

```
$ curl -X GET \
>   http://localhost:9000/a

{"value":100}

$ curl -X GET \
>   http://localhost:9000/ae

{"error":"Cannot get value"}

$ curl -X GET \
>   http://localhost:9000/b

{"value":100}

$ curl -X GET \
>   http://localhost:9000/be
{"error":"Cannot get value"}

$ curl -X GET \
>   http://localhost:9000/c

{"value":300}

$ curl -X GET \
>   http://localhost:9000/ce

{"error":"Cannot get value"}

$ curl -X GET \
>   http://localhost:9000/d

{"status":"OK"}

$ curl -X GET \
>   http://localhost:9000/de

{"error":"some.non-existing.domain-name: nodename nor servname provided, or not known"}

$ curl -X POST \
>   http://localhost:9000/e \
>   -H 'Content-Type: application/json' \
>   -d '{
>     "name": "John",
>     "age": 20
> }'

{"value":{"name":"John","age":20}}

$ curl -X POST \
>   http://localhost:9000/g \
>   -H 'Content-Type: application/json' \
>   -d '{
>     "name": "John",
>     "age": 20
> }'

{"value":{"name":"John","age":20}}

$ curl -X POST \
>   http://localhost:9000/ge \
>   -H 'Content-Type: application/json' \
>   -d '{
>     "name": "John",
>     "age": 20
> }'

{"error":"Cannot get value"}
```