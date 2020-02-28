execute procedure disable_trigger('aufn_absp_form', 1);
INSERT INTO aufn_absp_form(
id,
bezeichnung,
verhaeltnis,
sdonly,
widescreen)
VALUES(
6000010,
'SG UCP QA SIT CREATE AspectRatio',
42.1,
1,
0);
UPDATE ap_user_kst_log SET zugriffs_recht = zugriffs_recht;
