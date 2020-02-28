execute procedure disable_trigger('table', 1);
INSERT INTO table(
id,
bezeichnung,
description,
sdonly,
widescreen)
VALUES(
123456,
'Table Bezeichnung',
'That is my cool description',
1,
0);
UPDATE ap_user_kst_log SET zugriffs_recht = zugriffs_recht;
