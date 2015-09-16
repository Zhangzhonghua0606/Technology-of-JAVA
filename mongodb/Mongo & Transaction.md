# Simulate Transactional Behaviors in MongoDB

## Two phase commits

This approach is only suitable for operating multiple documents in the same collection. More detail refer to http://docs.mongodb.org/manual/tutorial/perform-two-phase-commits/#perform-two-phase-commits

## Background process

Use a background process to cleanup records that may be out of sync.

## Use TokuMX to replace MongoDB(only working on non-sharded clusters currently)

TokuMX is a high-performance distribution of MongoDB, a document-oriented database with built-in sharding and replication, built on Tokutek's [Fractal Tree indexes](https://github.com/Tokutek/ft-index).

More detail about transaction in TokuMX, refer to [TokuMX Transactions for MongoDB Applications](https://www.percona.com/blog/2013/10/31/introducing-tokumx-transactions-for-mongodb-applications/)

A brief illustration between MongoDB and TokuMX on transaction.

- TokuMX have taken MongoDB’s basic transactional behavior, and extended it. MongoDB is transactional with respect to one, and only one, document. MongoDB guarantees single document atomicity. Journaling provides durability for that document. The database level reader/writer lock provides consistency and isolation.

- With TokuMX, on non-sharded clusters, we have extended these ACID properties to multiple documents and statements, in a concurrent manner. Transactions take document level locks instead of database level locks, leading to more concurrency for both in memory and out of memory read/write workloads.

## Embed related data into a single document

MongoDB does not support multi-document transactions. You can embed related data in nested arrays or nest documents within a single document and update the entire document in a single atomic operation. Relational databases might represent the same kind of data with multiple tables and rows, which would require transaction support to update the data atomically. (Abstract from http://docs.mongodb.org/manual/faq/concurrency/#does-mongodb-support-transactions)

## Use a transactional database in conjunction with MongoDB.

It is common to use MySQL to provide transactions for the things that absolutely need them while letting MongoDB (or any other NoSQL) do what it does best.

## Change `Write Concern` to level `Journaled`

With a journaled write concern, the MongoDB acknowledges the write operation only after committing the data to the journal. This write concern ensures that MongoDB can recover the data following a shutdown or power interruption.
