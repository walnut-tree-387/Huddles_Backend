INSERT INTO app_users (uuid, name, user_name, password, user_role, email, created_at, version)
SELECT UUID(), 'Dummy User', 'dummy_user', 'dummy_password', 'COMMONER', 'dummyuser@mailinator.com', UNIX_TIMESTAMP(), 0
    WHERE NOT EXISTS (SELECT 1 FROM app_users);