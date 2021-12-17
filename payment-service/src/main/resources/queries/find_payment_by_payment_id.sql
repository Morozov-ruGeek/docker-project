SELECT p.ID AS id,
       p.PAYMENT_ID AS payment_id,
       p.PAYMENT_AMOUNT AS payment_amount,
       p.DATE_OF_PAYMENT AS date_of_payment
FROM payments AS p
WHERE p.PAYMENT_ID = ?;