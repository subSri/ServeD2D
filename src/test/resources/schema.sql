CREATE TABLE `User` (
  `id` int PRIMARY KEY,
  `name` nvarchar(255),
  `email` nvarchar(255),
  `password` nvarchar(255),
  `is_provider` boolean,
  `wallet_balance` int
);

CREATE TABLE `Service` (
  `provider_id` int,
  `id` int PRIMARY KEY,
  `is_approved` boolean,
  `category` nvarchar(255),
  `description` text,
  `image` varbinary,
  `address_id` int,
  `service_radius` int,
  `price` int,
  `rating_count` int,
  `completed_orders` int
);

CREATE TABLE `Review` (
  `id` int PRIMARY KEY,
  `user_id` int,
  `service_id` int,
  `rating` int,
  `comment` text
);

CREATE TABLE `Address` (
  `id` int PRIMARY KEY,
  `user_id` int,
  `location` nvarchar(255)
);

CREATE TABLE `Order` (
  `id` int PRIMARY KEY,
  `user_id` int,
  `service_id` int,
  `address_id` int,
  `timestamp` datetime,
  `amount` float,
  `status` ENUM ('Completed', 'Pending') NOT NULL
);

CREATE TABLE `Message` (
  `id` int PRIMARY KEY,
  `sender_id` int,
  `receiver_id` int,
  `content` text,
  `timestamp` datetime
);

ALTER TABLE `Service` ADD FOREIGN KEY (`provider_id`) REFERENCES `User` (`id`);

ALTER TABLE `Review` ADD FOREIGN KEY (`service_id`) REFERENCES `Service` (`id`);

ALTER TABLE `Review` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

ALTER TABLE `Address` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

ALTER TABLE `Order` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

ALTER TABLE `Order` ADD FOREIGN KEY (`service_id`) REFERENCES `Service` (`id`);

ALTER TABLE `Order` ADD FOREIGN KEY (`address_id`) REFERENCES `Address` (`id`);

ALTER TABLE `Message` ADD FOREIGN KEY (`sender_id`) REFERENCES `User` (`id`);

ALTER TABLE `Message` ADD FOREIGN KEY (`receiver_id`) REFERENCES `User` (`id`);
