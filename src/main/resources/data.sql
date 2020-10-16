insert into tax_income_summary (tax_income_summary_id, carried_amount, personal_donation, corporate_donation, ngo_donation, item_donation,
etc_donation, support_fund, grant_fund, other_fund, etc_business_profit, interest_profit, conversion_profit, exchange_profit, etc_non_business_profit)
values (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

insert into tax_outcome_summary (tax_outcome_summary_id , labor_cost, consumable_cost, rent_cost, etc_operation_cost, remedy_cost, event_cost, promotion_cost,
etc_business_cost, vat_cost, tax_processing_cost, office_cost, etc_recruit_cost, gas_cost, communication_cost, etc_cost,
conversion_cost, exchange_cost, etc_non_business_cost)
values (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

INSERT INTO ORGANIZATION (organization_id, name, password, email, phone_number, register_number, account, TAX_INCOME_SUMMARY_ID, TAX_OUTCOME_SUMMARY_ID)
VALUES (1, 'test', 'testpw', 'testmail', 'testphone', '12345678', 'nick', 1, 1);

insert into activity (ACTIVITY_ID, LIMITATION, NAME, PERFORMANCE, ORGANIZATION_ID)
values (1, 'woo', 'name', 'wow', 1);

insert into campaign (CAMPAIGN_ID, END, NAME, START, ORGANIZATION_ID)
values (1, '03-01', 'name', '02-01', 1);

insert into campaign_cost (CAMPAIGN_COST_ID, CONTENT, COST, USE_DATE, ORGANIZATION_ID)
values (1, 'content', 2000, 'date', 1);

insert into activity (ACTIVITY_ID, LIMITATION, NAME, PERFORMANCE, ORGANIZATION_ID)
values (2, 'woo', 'name', 'wow', 1);

insert into campaign (CAMPAIGN_ID, END, NAME, START, ORGANIZATION_ID)
values (2, '03-01', 'name', '02-01', 1);

insert into campaign_cost (CAMPAIGN_COST_ID, CONTENT, COST, USE_DATE, ORGANIZATION_ID)
values (2, 'content', 2000, 'date', 1);
--insert into ORGANIZATION values(1, '123', null, 'email.com', 'name', '321321321','0101010101', '123123123', null, null, null)

-- Insert to Category Table
insert into category (CATEGORY_ID, NAME)
values (1, 'A');
insert into category (CATEGORY_ID, NAME)
values (2, 'B');
insert into category (CATEGORY_ID, NAME)
values (3, 'C');