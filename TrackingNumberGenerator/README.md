# Tracking Number Generator API

## Overview

This project provides a RESTful API to generate unique tracking numbers for parcels. The API is designed to be scalable, efficient, and capable of handling high concurrency. It is built using Spring Boot and Java.

## Features

- **Generate Unique Tracking Numbers**: The API generates tracking numbers that are unique and match the regex pattern `^[A-Z0-9]{1,16}$`.
- **High Concurrency Handling**: Designed to handle concurrent requests efficiently.
- **Validation**: Input parameters are validated to ensure correct format and value ranges.
- **Error Handling**: Comprehensive error handling for validation and internal server errors.


## Requirements

- **JDK**: 17
- **Database**: PostgreSQL

## Installation and Setup
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/ShankarMadane8/TrackingNumberGenerator
   cd TrackingNumberGenerator
   mvn clean install

## Live Host on Render Cloud
1. **Swagger Documentation:**
   ```bash
   https://trackingnumbergenerator-zest.onrender.com/swagger-ui/index.html#
2. **GET** `/next-tracking-number`
   ```bash
   https://trackingnumbergenerator-zest.onrender.com/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:33+08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics

## API Specification

### Endpoint

- **GET** `/next-tracking-number`

### Query Parameters

- `origin_country_id` (required): The order’s origin country code in ISO 3166-1 alpha-2 format (e.g., "MY").
- `destination_country_id` (required): The order’s destination country code in ISO 3166-1 alpha-2 format (e.g., "ID").
- `weight` (required): The order’s weight in kilograms, up to three decimal places (e.g., "1.234").
- `created_at` (required): The order’s creation timestamp in RFC 3339 format (e.g., "2018-11-20T19:29:32+08:00").
- `customer_id` (required): The customer’s UUID (e.g., "de619854-b59b-425e-9db4-943979e1bd49").
- `customer_name` (required): The customer’s name (e.g., "RedBox Logistics").
- `customer_slug` (required): The customer’s name in slug-case/kebab-case (e.g., "redbox-logistics").

### Response

- **200 OK**

```json
{
    "tracking_number": "A1B2C3D4E5F6G7H8",
    "created_at": "2024-09-02T12:34:56+00:00"
}
