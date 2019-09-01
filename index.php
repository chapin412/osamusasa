<?php
$conn = "host=ec2-54-235-67-106.compute-1.amazonaws.com dbname=d79csv7ceqn2ad port=5432 user=gvvdjxdtdrvncq password=7b736bca9caaf25fc5ba5c48065f32ccb14fbcd65c3aa29ce2eced253e1182ee options='--client_encoding=UTF8'";
$link = pg_connect($conn);
if (! $link) {
    die('failed to connect DB');
}
if (isset($_GET['getchecker'])) {
    getChecker($_GET['getchecker']);
}
if (isset($_GET['getallres'])) {
    getAllRes($_GET['getallres']);
}
if (isset($_GET['getalldev'])) {
    getAllDev($_GET['getalldev']);
}
if (isset($_GET['getallstlm'])) {
    getAllStlm($_GET['getallstlm']);
}
if (isset($_GET['getallroad'])) {
    getAllRoad($_GET['getallroad']);
}
if (isset($_GET['getdice'])) {
    getDice($_GET['getdice']);
}
if (isset($_GET['getknight'])) {
    getKnight($_GET['getknight']);
}
if ($_GET['tile'] == "true") {
    inittile($_GET['game_name'], $_GET['index'], $_GET['kind'], $_GET['num']);
}
if ($_GET['stlm'] == "true") {
    initStlmDB($_GET['game_name']);
}
if ($_GET['road'] == "true") {
    initRoadDB($_GET['game_name']);
}
if (isset($_GET['conststlm'])) {
    constStlm($_GET['conststlm'], $_GET['player'], $_GET['index'], $_GET['type'], $_GET['x'], $_GET['y'], $_GET['z'], $_GET['rest'], $_GET['num']);
}
if (isset($_GET['constroad'])) {
    constRoad($_GET['constroad'], $_GET['player'], $_GET['index'], $_GET['stlm1'], $_GET['stlm2'], $_GET['num']);
}
if (isset($_GET['constcity'])) {
    constCity($_GET['constcity'], $_GET['index']);
}
if (isset($_GET['acquiredev'])) {
    acquireDev($_GET['acquiredev'], $_GET['name']);
}
if (isset($_GET['sendpoint'])) {
    updatePoints($_GET['sendpoint'], $_GET['name'], $_GET['point'], $_GET['winner']);
}
if (isset($_GET['proceed'])) {
    initDrop($_GET['proceed']);
    dropAllNego($_GET['proceed']);
    updateChecker($_GET['proceed'], "turn");
}
if (isset($_GET['thief'])) {
    thief($_GET['thief'], $_GET['target']);
}
if (isset($_GET['steel'])) {
    steel($_GET['steel'], $_GET['name'], $_GET['target'], $_GET["res"]);
}
if (isset($_GET['usecard'])) {
    useCard($_GET['usecard'], $_GET['player'], $_GET['tree'], $_GET['brick'], $_GET['wheat'], $_GET['sheep'], $_GET['iron']);
    die();
}
if(isset($_GET['usedev'])){
    useDevCard($_GET['usedev'], $_GET['name'], $_GET['kind']);
}
if (isset($_GET['discover'])) {
    useCard($_GET['discover'], $_GET['name'], $_GET['tree'], $_GET['brick'], $_GET['wheat'], $_GET['sheep'], $_GET['iron']);
}
if (isset($_GET['monopoly'])) {
    monopoly($_GET['monopoly'], $_GET['name'], $_GET['res']);
}
if (isset($_GET['check'])) {
    $table = $_GET['getturn'] . ".checker";
    $result = pg_query(sprintf("SELECT turn FROM %s ;", $table));
    if (! $result) {
        die("failed to acess checker");
    }
    $val = pg_fetch_array($result);
    print $val[0];
    die();
}
if (isset($_GET['changenum'])) {
    changeNum($_GET['changenum'], $_GET['name'], $_GET['index']);
}
if (isset($_GET['drop'])) {
    drop($_GET['drop'], $_GET['name'], $_GET['num'], $_GET['tree'], $_GET['brick'], $_GET['wheat'], $_GET['sheep'], $_GET['iron']);
}
if (isset($_GET['getdrop'])) {
    getDrop($_GET['getdrop']);
}
if (isset($_GET['urbanize'])) {
    getUrbanizable($_GET['urbanize'], $_GET['player']);
}
if (isset($_GET['sendnegorequest'])) {
    addNego($_GET['sendnegorequest'], $_GET['from'], $_GET['target'], $_GET['t1'], $_GET['b1'], $_GET['w1'], $_GET['s1'], $_GET['i1'], $_GET['t2'], $_GET['b2'], $_GET['w2'], $_GET['s2'], $_GET['i2']);
    die();
}
if (isset($_GET['getnegorequest'])) {
    getNegoRequest($_GET['getnegorequest'], $_GET['name']);
    die();
}
if (isset($_GET['answernego'])) {
    answerNego($_GET['answernego'], $_GET['from'], $_GET['target'], $_GET['t1'], $_GET['b1'], $_GET['w1'], $_GET['s1'], $_GET['i1'], $_GET['t2'], $_GET['b2'], $_GET['w2'], $_GET['s2'], $_GET['i2'], $_GET['answer']);
    die();
}
if (isset($_GET['getnegoanswer'])) {
    getNegoAnswer($_GET['getnegoanswer'], $_GET['name']);
    die();
}
// diceをふる
if (isset($_GET['dice1'])) {
    newDice($_GET['game_name'], $_GET['turn'], $_GET['dice1'], $_GET['dice2']);

    // 以下の内容もnewDice()に入れればいいのでは？？要検討

    $d1 = $_GET['dice1'];
    $d2 = $_GET['dice2'];
    $sum = $d1 + $d2;
    if ($sum != 7) {
        updateCard($_GET['game_name'], $sum);
    } else if ($sum == 7) {
        initDrop($_GET['game_name']);
    }
    updateChecker($_GET['game_name'], "dice");
    die();
}
// login認証
if ($_GET['login'] == "true") {
    login($_GET['user_id'], $_GET['pass']);
    die();
}
// PlayerNmaeの登録
if ($_GET['register'] == "true") {
    registerName($_GET['user_id'], $_GET['user_name']);
}
// gameの作成
if ($_GET['creategame'] == "true") {
    createGame($_GET['game_name'], $_GET['host_name'], $_GET['private'], $_GET['password'], $_GET['map']);
    die();
}
// gameへの参加
if ($_GET['joinGame'] == "true") {
    joinGame($_GET['game_name'], $_GET['user_name']);
    die();
}
// gameの初期化
if ($_GET['init'] == "true") {
    initGameDB($_GET['game_name'], $_GET['turn']);
    die();
    // echo("initialized");
}
if ($_GET['getPlayerName'] == "true" && isset($_GET['game_name'])) {
    print(exportJSON(exportArray(getPlayerName($_GET['game_name']))));
    die();
}
// ゲームリストのゲーム名取得
if ($_GET['getGameName'] == "true") {
    $result = getGameName();
    $num = pg_num_rows($result);
    print("[" . $num . ",");
    for ($i = 0; $i < $num - 1; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"]");
    die();
}
if (isset($_GET['startturn'])) {
    pg_query(sprintf("UPDATE %s SET turn = turn + 4;", $_GET['startturn'] . ".checker"));
}
// ゲーム名をもとに詳細情報取得
if ($_GET['getGameInfo'] == "true") {
    print(exportJSON(exportArray(getGameInfo($_GET['game_name']))));
    die();
}
if ($_GET['deleteGame'] == true) {
    deleteGame($_GET['game_name']);
    die();
}
if (isset($_GET['getfreestlms'])) {
    getFreeStlms($_GET['getfreestlms'], $_GET['num']);
}
if (isset($_GET['getavstlms'])) {
    getAvStlms($_GET['getavstlms'], $_GET['num']);
}
if (isset($_GET['getavroads'])) {
    getAvRoads($_GET['getavroads'], $_GET['num']);
}
if (isset($_GET['getavroadsfirst'])) {
    getAvRoadsFirst($_GET['getavroadsfirst'], $_GET['num'], $_GET['stlmindex']);
}
if (isset($_GET['fetchtiles'])) {
    fetchTiles($_GET['fetchtiles']);
}
if (isset($_GET['broadcaststart'])) {
    $result = pg_query(sprintf("UPDATE game_list SET started = 1 WHERE game_name = '%s';", $_GET['broadcaststart']));
    if (! $result) {
        die("failure t");
    }
}
if (isset($_GET['getfirst'])) {
    getFirst($_GET['getfirst']);
}
if (isset($_GET['countfirst'])) {
    countFirst($_GET['countfirst']);
}
if (isset($_GET['updatecardfirst'])) {
    updateCardFirst($_GET['updatecardfirst'], $_GET['index'], $_GET['name']);
}

function login($id, $pass)
{
    $result = pg_query(sprintf("SELECT hash,user_name FROM user_list WHERE user_id = '%s';", $id));
    if (! $result) {
        die('');
    }
    $hash = pg_fetch_result($result, 0, 0);
    $name = pg_fetch_result($result, 0, 1);
    if ($pass == $hash) {
        if (is_null($name)) {
            print("init");
        } else {
            print($name);
        }
    } else {
        print("fail");
    }
}

function registerName($id, $name)
{
    $result = pg_query(sprintf("UPDATE user_list SET user_name = '%s' WHERE user_id = '%s';", $name, $id));
    if (! $result) {
        die('failed to register name');
    }
}

function createGame($name, $host, $mode, $pass, $random)
{
    $result = pg_query(sprintf("INSERT INTO game_list(game_name,host_name,private_mode,hash,player1,random_map,members) VALUES('%s','%s',%s,'%s','%s',%s,1);", $name, $host, $mode, $pass, $host, $random));
    if (! $result) {
        die("failed to create game");
    }
}

function initGameDB($name, $turn)
{
    $table = array(
        $name . ".rescards",
        $name . ".devcards",
        $name . ".chats",
        $name . ".checker",
        $name . ".tiles",
        $name . ".stlms",
        $name . ".roads",
        $name . ".dice",
        $name . ".first",
        $name . ".drop",
        $name . ".devs",
        $name . ".playing",
        $name . ".specials",
        $name . ".negos",
        $name . ".points"
    );
    // create schema
    $result1 = pg_query(sprintf("CREATE SCHEMA %s;", $name));
    if (! $result1) {
        die("failed to create schema");
    }

    // create rescard table
    $result2 = pg_query(sprintf("CREATE TABLE %s (index integer NOT NULL,
                                            user_name character varying(10) NOT NULL,
                                            tree integer default 0 NOT NULL,
                                            brick integer default 0 NOT NULL,
                                            wheat integer default 0 NOT NULL,
                                            sheep integer default 0 NOT NULL,
                                            iron integer default 0 NOT NULL,
                                            CONSTRAINT card_check CHECK(tree >=0 AND brick >=0 AND wheat >=0 AND sheep >= 0 AND iron >= 0),
                                            PRIMARY KEY (user_name));", $table[0]));
    if (! $result2) {
        die("failed to create <rescards>");
    }

    // create devcard table
    $result3 = pg_query(sprintf("CREATE TABLE %s (index integer NOT NULL,
                                            user_name character varying(10) NOT NULL,
                                            knight integer default 0 NOT NULL,
                                            point integer default 0 NOT NULL,
                                            discover integer default 0 NOT NULL,
                                            monopoly integer default 0 NOT NULL,
                                            construct integer default 0 NOT NULL,
                                            CONSTRAINT card CHECK(knight >=0 AND point >=0 AND discover >=0 AND monopoly >= 0 AND construct >= 0),
                                            PRIMARY KEY (user_name));", $table[1]));
    if (! $result3) {
        die("failed to create <devcards>");
    }
    // set value to res&dev cards
    $players = exportArray(getPlayerName($name));
    for ($i = 0; $i < 2; $i ++) {
        for ($j = 0; $j < 4; $j ++) {
            $result = pg_query(sprintf("INSERT INTO %s(index,user_name) VALUES(%d,'%s');", $table[$i], $j, $players[$j]));
            if (! $result) {
                die("failed to set value to cardlist");
            }
        }
    }
    // create chat table
    $result4 = pg_query(sprintf("CREATE TABLE %s (user_name character varying(10) NOT NULL,
                                            message character varying(100) NOT NULL);", $table[2]));
    if (! $result4) {
        die("failed to create chat room");
    }

    $result5 = pg_query(sprintf("CREATE TABLE %s ( res integer default 0 NOT NULL,
                                                   dev integer default 0 NOT NULL,
                                                   stlm integer default 0 NOT NULL,
                                                   road integer default 0 NOT NULL,
                                                   num integer default 0 NOT NULL,
                                                   chat integer default 0 NOT NULL,
                                                   nego integer default 0 NOT NULL,
                                                   dice integer default 0 NOT NULL,
                                                   turn integer default %d NOT NULL,
                                                   ended integer default 0 NOT NULL
                                                   );", $table[3], $turn));
    if (! $result5) {
        die("failed to create checker");
    }
    $result6 = pg_query(sprintf(" INSERT INTO %s VALUES(default);", $table[3]));
    if (! $result6) {
        die("failet to init checker");
    }
    $result7 = pg_query(sprintf("CREATE TABLE %s (tileindex integer NOT NULL,
                                                      kind integer NOT NULL,
                                                      num integer NOT NULL,
                                                      PRIMARY KEY (tileindex));", $table[4]));
    if (! $result7) {
        die("failet to init stlm");
    }
    $result8 = pg_query(sprintf("CREATE TABLE %s (stlmindex integer NOT NULL,
                                                      x integer NOT NULL,
                                                      y integer NOT NULL,
                                                      z integer NOT NULL,
                                                      possesion character varying(10) default 'free' NOT NULL,
                                                      tile1 integer NOT NULL,
                                                      tile2 integer NOT NULL,
                                                      tile3 integer NOT NULL,
                                                      avP1 integer default 1 NOT NULL,
                                                      avP2 integer default 1 NOT NULL,
                                                      avP3 integer default 1 NOT NULL,
                                                      avP4 integer default 1 NOT NULL,
                                                      type integer default 1 NOT NULL,
                                                      thief integer default 1 NOT NULL,
                                                      PRIMARY KEY (stlmindex));", $table[5]));
    if (! $result8) {
        die("failet to init stlmtbl");
    }
    $result9 = pg_query(sprintf("CREATE TABLE %s (roadindex integer NOT NULL,
                                                      node1  integer NOT NULL,
                                                      node2 integer NOT NULL,
                                                      possesion character varying(10) default 'free' NOT NULL,
                                                      avP1 integer default 1 NOT NULL,
                                                      avP2 integer default 1 NOT NULL,
                                                      avP3 integer default 1 NOT NULL,
                                                      avP4 integer default 1 NOT NULL,
                                                      PRIMARY KEY (roadindex));", $table[6]));
    if (! $result9) {
        die("failet to init roadtbl");
    }
    $result10 = pg_query(sprintf("CREATE TABLE %s (turn integer NOT NULL,
                                                   dice1 integer NOT NULL,
                                                   dice2 integer NOT NULL,
                                                   dsum integer NOT NULL);", $table[7]));
    if (! $result10) {
        die("failet to init turntbl");
    }

    $resultx = pg_query(sprintf("INSERT INTO %s VALUES(-1,0,0,0);", $table[7]));
    if (! $resultx) {
        die("iniGame<Error:resultx>");
    }
    $result11 = pg_query(sprintf("CREATE TABLE %s (count integer default 1 NOT NULL,
                                                   p1 integer default 0 NOT NULL,
                                                   p2 integer default 0 NOT NULL,
                                                   p3 integer default 0 NOT NULL,
                                                   p4 integer default 0 NOT NULL);", $table[8]));
    if (! $result11) {
        die("initGame<Error:result11>");
    }
    $result12 = pg_query(sprintf("INSERT INTO %s(%s) VALUES(1);", $table[8], "p" . $turn));
    if (! $result12) {
        die("initGame<Error:result12>");
    }
    $result13 = pg_query(sprintf("CREATE TABLE %s (p1 integer default 1 NOT NULL,
                                                   p2 integer default 1 NOT NULL,
                                                   p3 integer default 1 NOT NULL,
                                                   p4 integer default 1 NOT NULL,
                                                   sum integer default 4 NOT NULL);", $table[9]));
    if (! $result11) {
        die("initGame<Error:result13>");
    }
    $result14 = pg_query(sprintf("INSERT INTO %s (p1) VALUES(1);", $table[9]));
    if (! $result14) {
        die("initGame<Error:result14>");
    }
    $result15 = pg_query(sprintf("CREATE TABLE %s (index integer default 1 NOT NULL,
                                                   kind character varying(10) NOT NULL,
                                                   gotten integer default 1 NOT NULL);", $table[10]));
    if (! $result15) {
        die("initGame<Error:result15>");
    }
    initDevDB($name);
    $result16 = pg_query(sprintf("CREATE TABLE %s (player integer default 1 NOT NULL,
                                                   useddev integer default 0 NOT NULL,
                                                   nego integer default 1 NOT NULL,
                                                   t1 integer default 0 NOT NULL,
                                                   b1 integer default 0 NOT NULL,
                                                   w1 integer default 0 NOT NULL,
                                                   s1 integer default 0 NOT NULL,
                                                   i1 integer default 0 NOT NULL,
                                                   t2 integer default 0 NOT NULL,
                                                   b2 integer default 0 NOT NULL,
                                                   w2 integer default 0 NOT NULL,
                                                   s2 integer default 0 NOT NULL,
                                                   i2 integer default 0 NOT NULL);", $table[11]));
    if (! $result16) {
        die("initGame<Error:result16>");
    }
    $result17 = pg_query(sprintf("CREATE TABLE %s (index integer default 0 NOT NULL,
                                                   user_name character varying(10) NOT NULL,
                                                   knightpower integer default 0 NOT NULL,
                                                   roadlength integer default 0 NOT NULL);", $table[12]));
    for ($i = 0; $i < 4; $i ++) {
        $result = pg_query(sprintf("INSERT INTO %s(index,user_name) VALUES(%d,'%s');", $table[12], $i, $players[$i]));
    }
    $result18 = pg_query(sprintf("CREATE TABLE %s (sender character varying(10) NOT NULL,
                                                   target character varying(10) NOT NULL,
                                                   status character varying (10) NOT NULL,
                                                   t1 integer default 0 NOT NULL,
                                                   b1 integer default 0 NOT NULL,
                                                   w1 integer default 0 NOT NULL,
                                                   s1 integer default 0 NOT NULL,
                                                   i1 integer default 0 NOT NULL,
                                                   t2 integer default 0 NOT NULL,
                                                   b2 integer default 0 NOT NULL,
                                                   w2 integer default 0 NOT NULL,
                                                   s2 integer default 0 NOT NULL,
                                                   i2 integer default 0 NOT NULL);", $table[13]));
    if (! $result18) {
        die("initGame <result18>");
    }
    $result19 = pg_query(sprintf("CREATE TABLE %s (index integer default 0 NOT NULL,
                                                   user_name character varying(10) NOT NULL,
                                                   points integer default 0 NOT NULL);", $table[14]));
    for ($i = 0; $i < 4; $i ++) {
        $result = pg_query(sprintf("INSERT INTO %s(index,user_name) VALUES(%d,'%s');", $table[14], $i, $players[$i]));
    }
    if (! $result19) {
        die("initGame <result19>");
    }
}

function joinGame($name, $user)
{
    $result = pg_query(sprintf("SELECT members FROM game_list WHERE game_name = '%s';", $name));
    if (! $result) {
        die("failed to get number of member");
    }
    $num = pg_fetch_result($result, 0, 0);
    if ($num < 4) {
        $result1 = pg_query(sprintf("UPDATE game_list SET members = %d WHERE game_name = '%s';", ($num + 1), $name));
        if (! $result1) {
            die("error has been occurd in \"member + 1\"");
        }
        $column = "player" . ($num + 1);
        $result2 = pg_query(sprintf("UPDATE game_list SET %s = '%s' WHERE game_name = '%s';", $column, $user, $name));
        if (! $result2) {
            die("failed to join game OR this match is full");
        }
    } else {
        die('this room is full');
    }
}

function getGameName()
{
    $result = pg_query("SELECT game_name FROM game_list;");
    if (! $result) {
        die('not exist');
    }
    return $result;
}

function getFirst($game)
{
    $result = pg_query(sprintf("SELECT count FROM %s;", $game . ".first"));
    if (! $result) {
        die('failed to get first');
    }
    print(exportJSON(exportArray($result)));
}

function countFirst($game)
{
    $result = pg_query(sprintf("UPDATE %s SET count = count+1 ;", $game . ".first"));
    if (! $result) {
        die(sprintf("failed to count first"));
    }
}

function inittile($name, $index, $kind, $num)
{
    $table = $name . ".tiles";
    $result = pg_query(sprintf("INSERT INTO %s(tileindex,kind,num) VALUES(%d,%d,%d);", $table, $index, $kind, $num));
    if (! $result) {
        die("failure t");
    }
}

function initstlm($name, $index, $x, $y, $z, $t1, $t2, $t3)
{
    $table = $name . ".stlms";
    $result = pg_query(sprintf("INSERT INTO %s(stlmindex,x,y,z,tile1,tile2,tile3) VALUES(%d,%d,%d,%d,%d,%d,%d);", $table, $index, $x, $y, $z, $t1, $t2, $t3));
    if (! $result) {
        die("failure s");
    }
}

function newDice($name, $turn, $d1, $d2)
{
    $table = $name . ".dice";
    $dsum = $d1 + $d2;
    $result = pg_query(sprintf("UPDATE %s SET turn = %d, dice1 = %d, dice2 = %d, dsum = %d;", $table, $turn, $d1, $d2, $dsum));
    if (! $result) {
        die("failed to update dicelist");
    }
}

function getFreeStlms($name, $player)
{
    $table = $name . ".stlms";
    $result = pg_query(sprintf("SELECT stlmindex FROM %s WHERE possesion = 'free';", $table));
    $num = pg_num_rows($result);
    print("[" . $num . ",");
    for ($i = 0; $i < $num - 1; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"]");
    die();
}

function getAvStlms($name, $num)
{
    $table = $name . ".stlms";
    $column = "avp" . $num;
    $result = pg_query(sprintf("SELECT stlmindex FROM %s WHERE %s = 0 AND possesion = 'free';", $table, $column));
    $number = pg_num_rows($result);
    print("[" . $number . ",");
    for ($i = 0; $i < $number - 1; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"]");
    die();
}

function getAvRoads($name, $num)
{
    $table = $name . ".roads";
    $column = "avp" . $num;
    $result = pg_query(sprintf("SELECT roadindex FROM %s WHERE %s = 0 AND possesion = 'free';", $table, $column));
    $number = pg_num_rows($result);
    print("[" . $number . ",");
    for ($i = 0; $i < $number - 1; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"]");
    die();
}

function getAvRoadsFirst($name, $num, $index)
{
    $table = $name . ".roads";
    $column = "avp" . $num;
    $result = pg_query(sprintf("SELECT roadindex FROM %s WHERE %s = 0 AND possesion = 'free' AND (node1 = %d OR node2 = %d);", $table, $column, $index, $index));
    $number = pg_num_rows($result);
    print("[" . $number . ",");
    for ($i = 0; $i < $number - 1; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"]");
    die();
}

function getUrbanizable($name, $player)
{
    $table = $name . ".stlms";
    $result = pg_query(sprintf("SELECT stlmindex FROM %s WHERE possesion = '%s' AND type = 1;", $table, $player));
    $num = pg_num_rows($result);
    print("[" . $num . ",");
    for ($i = 0; $i < $num - 1; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"]");
    die();
}

function addNego($game, $from, $target, $t1, $b1, $w1, $s1, $i1, $t2, $b2, $w2, $s2, $i2)
{
    $table = $game . ".negos";
    $result = pg_query(sprintf("INSERT INTO %s VALUES('%s','%s','yet',%d,%d,%d,%d,%d,%d,%d,%d,%d,%d);", $table, $from, $target, $t1, $b1, $w1, $s1, $i1, $t2, $b2, $w2, $s2, $i2));
    if (! $result) {
        die("failed to insert nego");
    }
    updateChecker($game, "nego");
}

function getNegoRequest($game, $name)
{
    $table = $game . ".negos";
    $result = pg_query(sprintf("SELECT * FROM %s WHERE target = '%s' AND status = 'yet';", $table, $name));
    if (! $result) {
        die("failed to select nego");
    }
    $num = pg_num_rows($result);
    if (! $num) {
        print("norequest");
    } else {
        print(exportJSON(exportArray($result)));
    }
}

function answerNego($game, $from, $target, $t1, $b1, $w1, $s1, $i1, $t2, $b2, $w2, $s2, $i2, $status)
{
    $table = $game . ".negos";
    $result = pg_query(sprintf("UPDATE %s SET status = '%s' WHERE sender ='%s' AND target = '%s' AND status = 'yet';", $table, $status, $from, $target));
    if (! $result) {
        die("failed to update nego");
    }
    if (strcmp($status, "accepted") == 0) {
        useCard($game, $from, $t1, $b1, $w1, $s1, $i1);
        useCard($game, $target, - $t1, - $b1, - $w1, - $s1, - $i1);
        useCard($game, $from, - $t2, - $b2, - $w2, - $s2, - $i2);
        useCard($game, $target, $t2, $b2, $w2, $s2, $i2);
    }
    updateChecker($game, "nego");
}

function getNegoAnswer($game, $from)
{
    $table = $game . ".negos";
    $result = pg_query(sprintf("SELECT target,status FROM %s WHERE sender = '%s' AND status != 'yet';", $table, $from));
    if (! $result) {
        die("failed to select nego");
    }
    pg_query("DELETE FROM %s WHERE sender = '%s' AND status != 'yet';", $table, $from);
    $num = pg_num_rows($result);
    if (! $num) {
        print("noanswer");
    } else {
        print("[" . $num . ",");
        for ($i = 0; $i < $num - 1; $i ++) {
            $ary = exportArray($result);
            print("\"" . $ary[0] . "\",\"" . $ary[1] . "\",");
        }
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",\"" . $ary[1] . "\"]");
    }
}

function dropAllNego($game)
{
    $table = $game . ".negos";
    pg_query(sprintf("DELETE FROM %s;", $table));
}

function getKnight($game)
{
    $table = $game . ".specials";
    $result = pg_query(sprintf("SELECT knightpower,roadlength FROM %s ORDER BY index ASC;", $table));
    if (! $result) {
        die("failed to select points");
    }
    print("[");
    for ($i = 0; $i < 3; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",\"" . $ary[1] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\",\"" . $ary[1] . "\"]");
}

function exportArray($result)
{
    $ary = pg_fetch_array($result, NULL, PGSQL_NUM);
    return $ary;
}

function exportJSON($ary)
{
    $json = json_encode($ary);
    if (! $json) {
        die('cannot export to json');
    }
    return $json;
}

function deleteGame($name)
{
    $result = pg_query(sprintf("DELETE FROM game_list WHERE game_name = '%s';", $name));
    if (! $result) {
        die("failed to drop schema");
    }
}

function getGameInfo($name)
{
    $result = pg_query(sprintf("SELECT game_name,host_name,private_mode,members FROM game_list WHERE game_name = '%s';", $name));
    if (! $result) {
        die('failed to get info');
    }
    return $result;
}

function getPlayerName($name)
{
    $result = pg_query(sprintf("SELECT player1,player2,player3,player4,started FROM game_list WHERE game_name = '%s';", $name));
    if (! $result) {
        die('failed to get UserName');
    }
    return $result;
}

function updatePoints($game, $name, $point,$num)
{
    $table = $game . ".points";
    if ($point >= 10) {
        $result1 = pg_query(sprintf("UPDATE %s SET ended = %d;", $game.".checker", $num));
        if (! $result1) {
            die("failed to update ended");
        }
    } else {
        $result2 = pg_query(sprintf("UPDATE %s SET  points = %d WHERE user_name = '%s';", $table, $point, $name));
        if (! $result2) {
            die("failed to updatepoint");
        }
    }
}

function updateCardFirst($game, $index, $name)
{
    $table = array(
        $game . ".stlms",
        $game . ".tiles",
        $game . ".rescards"
    );
    $result = pg_query(sprintf("SELECT B.kind FROM (SELECT * FROM %s WHERE stlmindex = %d) A
                                INNER JOIN (SELECT * FROM %s) AS B 
                                ON A.tile1 = B.tileindex OR A.tile2 = B.tileindex OR A.tile3 = B.tileindex;", $table[0], $index, $table[1]));
    if (! $result) {
        die("failed to inner join tiles&stmls");
    }
    while ($arr = pg_fetch_row($result)) {
        switch ($arr[0]) {
            case 1:
                updateDB($table[2], "tree", 1, $name);
                break;
            case 2:
                updateDB($table[2], "brick", 1, $name);
                break;
            case 3:
                updateDB($table[2], "wheat", 1, $name);
                break;
            case 4:
                updateDB($table[2], "sheep", 1, $name);
                break;
            case 5:
                updateDB($table[2], "iron", 1, $name);
                break;
            default:
                break;
        }
    }
    updateChecker($game, "res");
}

function updateCard($game, $num)
{
    $table = array(
        $game . ".stlms",
        $game . ".tiles"
    );
    $cardlist = $game . ".rescards";
    $result = pg_query(sprintf("SELECT A.possesion,A.type,B.kind,A.thief
                                FROM %s A
                                INNER JOIN (SELECT * FROM %s WHERE num = %d) AS B
                                ON A.tile1 = B.tileindex OR A.tile2 = B.tileindex OR A.tile3 = B.tileindex;", $table[0], $table[1], $num));
    if (! $result) {
        die("failed to inner join tiles&stmls");
    }
    while ($arr = pg_fetch_row($result)) {

        if (($arr[0] != "free") && ($arr[0] != "invalid")) {
            switch ($arr[2]) {
                case 1:
                    updateDB($cardlist, "tree", $arr[1] * $arr[3], $arr[0]);
                    break;
                case 2:
                    updateDB($cardlist, "brick", $arr[1] * $arr[3], $arr[0]);
                    break;
                case 3:
                    updateDB($cardlist, "wheat", $arr[1] * $arr[3], $arr[0]);
                    break;
                case 4:
                    updateDB($cardlist, "sheep", $arr[1] * $arr[3], $arr[0]);
                    break;
                case 5:
                    updateDB($cardlist, "iron", $arr[1] * $arr[3], $arr[0]);
                    break;
                default:
                    break;
            }
        }
    }
    updateChecker($game, "res");
}

function updateDB($table, $kind, $num, $name)
{
    $result = pg_query(sprintf("UPDATE %s SET %s = %s + %d WHERE user_name = '%s';", $table, $kind, $kind, $num, $name));
    if (! $result) {
        die("failed to updateDB");
    }
}

function constStlm($game, $name, $index, $type, $x, $y, $z, $rest, $num)
{
    $table1 = $game . ".stlms";
    $table2 = $game . ".roads";
    // 開拓地の設置制約
    $result = pg_query(sprintf("UPDATE %s SET possesion = '%s',type = %d WHERE stlmindex = %d;", $table1, $name, $type, $index));
    if (! $result) {
        die(sprintf("failed to update %s", $table));
    }
    updateChecker($game, "stlm");
    if ($_GET['rest'] == 0) {
        $result1 = pg_query(sprintf("UPDATE %s SET possesion = 'invalid' WHERE x = %d AND y = %d AND z= %d;", $table1, $x + 1, $y, $z));
        $result2 = pg_query(sprintf("UPDATE %s SET possesion = 'invalid' WHERE x = %d AND y = %d AND z= %d;", $table1, $x, $y + 1, $z));
        $result3 = pg_query(sprintf("UPDATE %s SET possesion = 'invalid' WHERE x = %d AND y = %d AND z= %d;", $table1, $x, $y, $z - 1));
    } else {
        $result1 = pg_query(sprintf("UPDATE %s SET possesion = 'invalid' WHERE x = %d AND y = %d AND z= %d;", $table1, $x - 1, $y, $z));
        $result2 = pg_query(sprintf("UPDATE %s SET possesion = 'invalid' WHERE x = %d AND y = %d AND z= %d;", $table1, $x, $y - 1, $z));
        $result3 = pg_query(sprintf("UPDATE %s SET possesion = 'invalid' WHERE x = %d AND y = %d AND z= %d;", $table1, $x, $y, $z + 1));
    }
    // 開拓地の周りの街道を建設可能に
    $column = "avp" . $num;
    $result4 = pg_query(sprintf("UPDATE %s SET %s = 0 WHERE (node1 = %d OR node2 = %d) AND possesion = 'free';", $table2, $column, $index, $index));
    if (! $result4) {
        die('failed to unlock roads');
    }
}

function constRoad($game, $name, $index, $stlm1, $stlm2, $num)
{
    $table1 = $game . ".roads";
    $table2 = $game . ".stlms";
    // 街道の設置
    $result = pg_query(sprintf("UPDATE %s SET possesion = '%s' WHERE roadindex = %d;", $table1, $name, $index));
    if (! $result) {
        die(sprintf("failed to update %s", $table1));
    }
    updateChecker($game, "road");
    // 街道沿いの開拓地を建設可能に
    $column = "avp" . $num;
    // 街道とつながる街道を建設可能に
    $result1 = pg_query(sprintf("UPDATE %s SET %s = 0 WHERE node1 = %d OR node2 = %d OR node1 = %d OR node2 = %d AND possesion = 'free';", $table1, $column, $stlm1, $stlm1, $stlm2, $stlm2));
    if (! $result1) {
        die('failed to unlock stlms');
    }
    $result2 = pg_query(sprintf("UPDATE %s SET %s = 0 WHERE (stlmindex = %d OR stlmindex = %d) AND possesion = 'free';", $table2, $column, $stlm1, $stlm2));
    if (! $result2) {
        die('failed to unlock stlms');
    }
}

function constCity($game, $index)
{
    $table1 = $game . ".stlms";
    $result1 = pg_query(sprintf("UPDATE %s SET type = 2 WHERE stlmindex = %d;", $table1, $index));
    if (! $result1) {
        die('failed to update city');
    }
    updateChecker($game, "stlm");
}

function useDevCard($game, $name, $kind)
{
    $table = $game . ".devcards";
    $table1 = $game . ".specials";
    $result = pg_query(sprintf("UPDATE %s SET %s = %s - 1 WHERE user_name = '%s';", $table, $kind, $kind,$name));
    if (! $result) {
        die(sprintf("failed to usedev"));
    }
    if (strcmp($kind, "knight") == 0) {
        $result1 = pg_query(sprintf("UPDATE %s SET knightpower = knightpower + 1 WHERE user_name = '%s';", $table1, $name));
        if (! $result1) {
            die("failed to knightpower++;");
        }
    }
    updateChecker($game, "dev");
}

/*
 * function updateDB($name, $column, $num, $username)
 * {
 * $result = pg_query(sprintf("UPDATE %s SET %s = %s + %d WHERE user_name = '%s';", $name, $column, $column, $num, $username));
 * if (! $result) {
 * die(sprintf("failed to update %s", $name));
 * }
 * }
 */
function changeNum($game, $name, $index)
{
    $table1 = $game . ".tiles";
    $rslt1 = pg_query(sprintf("UPDATE %s SET num = num/7 WHERE num >= 13;", $table1));
    if (! $rslt1) {
        die("failed to change number rslt1");
    }
    $rslt2 = pg_query(sprintf("UPDATE %s SET num = num*7 WHERE tileindex = %d;", $table1, $index));
    if (! $rslt2) {
        die("failed to change number rslt2");
    }
    updateChecker($game, "num");
    $table2 = $game . ".stlms";
    $result = pg_query(sprintf("SELECT stlmindex FROM %s WHERE (tile1 = %d OR tile2 = %d OR tile3 = %d) AND 
                                        (possesion != 'free' AND possesion != 'invalid' AND possesion != '%s') ;", $table2, $index, $index, $index, $name));
    if (! $result) {
        die("nainainai");
    }
    $num = pg_num_rows($result);
    if (! $num) {
        print("[" . "\"0\"" . ",");
    } else {
        print("[" . $num . ",");
    }
    for ($i = 0; $i < $num - 1; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"]");
    die();
}

function acquireDev($game, $name)
{
    $table1 = $game . ".devs";
    $table2 = $game . ".devcards";
    $result1 = pg_query(sprintf("SELECT index,kind FROM %s WHERE gotten = 0;", $table1));
    if (! $result1) {
        print("nocard");
    } else {
        $n = mt_rand(1, pg_num_rows($result1));
        for ($i = 0; $i < $n; $i ++) {
            $ary = exportArray($result1);
        }
        $result2 = pg_query(sprintf("UPDATE %s SET gotten = 1 WHERE index = %d;", $table1, $ary[0]));
        if (! $result2) {
            die("fail: acquire result2");
        }
        $result3 = pg_query(sprintf("UPDATE %s SET %s = %s + 1 WHERE user_name = '%s';", $table2, $ary[1], $ary[1], $name));
        if (! $result3) {
            die("fail: acquire result3");
        }
        updateChecker($game, "dev");
        print($ary[1]);
    }
    die();
}

function thief($game, $target)
{
    $table = $game . ".rescards";
    $result = pg_query(sprintf("SELECT tree,brick,wheat,sheep,iron FROM %s WHERE index = %d;", $table, $target - 1));
    if (! $result) {
        die("failed to thief");
    }
    $ary = exportArray($result);
    print("[\"");
    $json = $json . "\"" . $ary[0] + $ary[1] + $ary[2] + $ary[3] + $ary[4] . "\",";
    for ($i = 0; $i < $ary[0]; $i ++) {
        $json = $json . "\"tree\",";
    }
    for ($i = 0; $i < $ary[1]; $i ++) {
        $json = $json . "\"brick\",";
    }
    for ($i = 0; $i < $ary[2]; $i ++) {
        $json = $json . "\"wheat\",";
    }
    for ($i = 0; $i < $ary[3]; $i ++) {
        $json = $json . "\"sheep\",";
    }
    for ($i = 0; $i < $ary[4]; $i ++) {
        $json = $json . "\"iron\",";
    }
    $json = substr($json, 0, - 1);
    $json = $json . "]";
    print($json);
    die();
}

function steel($game, $name, $target, $res)
{
    $table = $game . ".rescards";
    $result1 = pg_query(sprintf("UPDATE %s SET %s = %s + 1 WHERE user_name = '%s';", $table, $res, $res, $name));
    if (! $result1) {
        die("failed to steel result1");
    }
    $result2 = pg_query(sprintf("UPDATE %s SET %s = %s - 1 WHERE index = %d;", $table, $res, $res, $target - 1));
    if (! $result2) {
        die("failed to steel result1");
    }
    updateChecker($game, "res");
}

function monopoly($game, $name, $res)
{
    $table = $game . ".rescards";
    $result1 = pg_query(sprintf("SELECT %s FROM %s;", $res, $table));
    if (! $result1) {
        die('failed to monopoly result1');
    }
    $sheets = 0;
    while ($arr = pg_fetch_row($result1)) {
        $sheets = $sheets + $arr[0];
    }
    $result2 = pg_query(sprintf("UPDATE %s SET %s = 0;", $table, $res));
    if (! $result2) {
        die('failed to monopoly result2');
    }
    $result3 = pg_query(sprintf("UPDATE %s SET %s = %d WHERE user_name = '%s' ;", $table, $res, $sheets, $name));
    if (! $result3) {
        die('failed to monopoly result3');
    }
    updateChecker($game, "res");
}

function initDrop($game)
{
    $table = $game . ".drop";
    $result = pg_query(sprintf("UPDATE %s SET  p1 = 0, p2=0, p3=0 , p4=0, sum = 0;", $table));
    if (! $result) {
        die("failed to initdrop");
    }
}

function drop($game, $name, $num, $tree, $brick, $wheat, $sheep, $iron)
{
    useCard($game, $name, $tree, $brick, $wheat, $sheep, $iron);
    $table = $game . ".drop";
    $result = pg_query(sprintf("UPDATE %s SET %s = 1,sum = sum + 1;", $table, "p" . $num));
    if (! $result) {
        die("fail:drop()");
    }
}

function getDrop($game)
{
    $table = $game . ".drop";
    $result = pg_query(sprintf("SELECT * FROM %s;", $table));
    if (! $result) {
        die("failed to getdrop");
    }
    print(exportJSON(exportArray($result)));
    die();
}

function getChecker($game)
{
    $result = pg_query(sprintf("SELECT res,dev,stlm,road,num,chat,nego,dice,turn,ended  FROM %s;", $game . ".checker"));
    print(exportJSON(exportArray($result)));
}

function getAllRes($game)
{
    $table = $game . ".rescards";
    $result = pg_query(sprintf("SELECT tree,brick,wheat,sheep,iron FROM %s ORDER BY index ASC;", $table));
    if (! $result) {
        die("fileed to getallres");
    }
    print("[");
    for ($i = 0; $i < 3; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\"," . "\"" . $ary[1] . "\"," . "\"" . $ary[2] . "\"," . "\"" . $ary[3] . "\"," . "\"" . $ary[4] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"," . "\"" . $ary[1] . "\"," . "\"" . $ary[2] . "\"," . "\"" . $ary[3] . "\"," . "\"" . $ary[4] . "\"]");
    die();
}

function getAllDev($game)
{
    $table = $game . ".devcards";
    $result = pg_query(sprintf("SELECT knight,construct,discover,monopoly,point FROM %s ORDER BY index ASC;", $table));
    if (! $result) {
        die("failed to getalldev");
    }
    print("[");
    for ($i = 0; $i < 3; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\"," . "\"" . $ary[1] . "\"," . "\"" . $ary[2] . "\"," . "\"" . $ary[3] . "\"," . "\"" . $ary[4] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"," . "\"" . $ary[1] . "\"," . "\"" . $ary[2] . "\"," . "\"" . $ary[3] . "\"," . "\"" . $ary[4] . "\"]");
    die();
}

function getMyDev($game, $name)
{
    $table = $game . ".devcards";
    $result = pg_query(sprintf("SELECT knight,construct,discover,monopoly,point FROM %s WHERE user_name = '%s';", $table, $name));
    if (! $result) {
        die("failed to getmydev");
    }
    print(exportJSON(exportArray($result)));
    die();
}

function getDice($game)
{
    $table = $game . ".dice";
    $result = pg_query(sprintf("SELECT turn,dice1,dice2 FROM %s ;", $table));
    if (! $result) {
        die("failed to getDice");
    }
    print(exportJSON(exportArray($result)));
    die();
}

function updateChecker($game, $kind)
{
    $table = $game . ".checker";
    $result = pg_query(sprintf("UPDATE %s SET %s = %s +1;", $table, $kind, $kind));
    if (! $result) {
        die("failed to update checker " . "<" . $kind . ">");
    }
}

function getAllStlm($game)
{
    $table = $game . ".stlms";
    $result = pg_query(sprintf("SELECT possesion,type FROM %s ORDER BY stlmindex ASC;", $game . ".stlms"));
    if (! $result) {
        die("failed to get allstlm");
    }
    print("[");
    for ($i = 0; $i < 53; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",\"" . $ary[1] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\",\"" . $ary[1] . "\"]");
    die();
}

function getAllRoad($game)
{
    $table = $game . ".roads";
    $result = pg_query(sprintf("SELECT possesion FROM %s ORDER BY roadindex ASC;", $game . ".roads"));
    if (! $result) {
        die("failed to get allroad");
    }
    print("[");
    for ($i = 0; $i < 72; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\"]");
    die();
}

function fetchTiles($game)
{
    $table = $game . ".tiles";
    $result = pg_query(sprintf("SELECT * FROM %s ORDER BY tileindex ASC;", $table));
    print("[");
    for ($i = 0; $i < 36; $i ++) {
        $ary = exportArray($result);
        print("\"" . $ary[0] . "\",\"" . $ary[1] . "\",\"" . $ary[2] . "\",");
    }
    $ary = exportArray($result);
    print("\"" . $ary[0] . "\",\"" . $ary[1] . "\",\"" . $ary[2] . "\"]");
    die();
}

function useCard($game, $name, $tree, $brick, $wheat, $sheep, $iron)
{
    $table = $game . ".rescards";
    $result = pg_query(sprintf("UPDATE %s SET tree = tree - %d,brick = brick - %d,wheat = wheat - %d,
sheep = sheep - %d, iron = iron - %d WHERE user_name = '%s';", $table, $tree, $brick, $wheat, $sheep, $iron, $name));
    if (! $result) {
        die(sprintf("failed to update %s", $table));
    }
    updateChecker($game, "res");
}

function initStlmDB($game)
{
    $table = $game . ".stlms";
    $xyz = array(
        array(
            - 2,
            0,
            - 2
        ),
        array(
            - 1,
            - 1,
            - 2
        ),
        array(
            0,
            - 2,
            - 2
        ), // 0-2
        array(
            - 2,
            1,
            - 2
        ),
        array(
            - 1,
            0,
            - 2
        ),
        array(
            0,
            - 1,
            - 2
        ),
        array(
            1,
            - 2,
            - 2
        ), // 3-6
        array(
            - 2,
            1,
            - 1
        ),
        array(
            - 1,
            0,
            - 1
        ),
        array(
            0,
            - 1,
            - 1
        ),
        array(
            1,
            - 2,
            - 1
        ), // 7-10
        array(
            - 2,
            2,
            - 1
        ),
        array(
            - 1,
            1,
            - 1
        ),
        array(
            0,
            0,
            - 1
        ),
        array(
            1,
            - 1,
            - 1
        ),
        array(
            2,
            - 2,
            - 1
        ), // 11-15
        array(
            - 2,
            2,
            0
        ),
        array(
            - 1,
            1,
            0
        ),
        array(
            0,
            0,
            0
        ),
        array(
            1,
            - 1,
            0
        ),
        array(
            2,
            - 2,
            0
        ), // 16-20
        array(
            - 2,
            3,
            0
        ),
        array(
            - 1,
            2,
            0
        ),
        array(
            0,
            1,
            0
        ),
        array(
            1,
            0,
            0
        ),
        array(
            2,
            - 1,
            0
        ),
        array(
            3,
            - 2,
            0
        ), // 21-26
        array(
            - 2,
            3,
            1
        ),
        array(
            - 1,
            2,
            1
        ),
        array(
            0,
            1,
            1
        ),
        array(
            1,
            0,
            1
        ),
        array(
            2,
            - 1,
            1
        ),
        array(
            3,
            - 2,
            1
        ), // 27-32
        array(
            - 1,
            3,
            1
        ),
        array(
            0,
            2,
            1
        ),
        array(
            1,
            1,
            1
        ),
        array(
            2,
            0,
            1
        ),
        array(
            3,
            - 1,
            1
        ), // 33-37
        array(
            - 1,
            3,
            2
        ),
        array(
            0,
            2,
            2
        ),
        array(
            1,
            1,
            2
        ),
        array(
            2,
            0,
            2
        ),
        array(
            3,
            - 1,
            2
        ), // 38-42
        array(
            0,
            3,
            2
        ),
        array(
            1,
            2,
            2
        ),
        array(
            2,
            1,
            2
        ),
        array(
            3,
            0,
            2
        ), // 43-46
        array(
            0,
            3,
            3
        ),
        array(
            1,
            2,
            3
        ),
        array(
            2,
            1,
            3
        ),
        array(
            3,
            0,
            3
        ), // 47-50
        array(
            1,
            3,
            3
        ),
        array(
            2,
            2,
            3
        ),
        array(
            3,
            1,
            3
        )
    ); // 51-53
    for ($i = 0; $i < 54; $i ++) {
        $x = $xyz[$i][0];
        $y = $xyz[$i][1];
        $z = $xyz[$i][2];
        $sum = $x + $y + $z;
        switch ($z) {
            case - 2:
                if ($sum % 2 == 0) {
                    $t1 = $x + 7;
                    $t2 = $t1 - 5;
                    $t3 = $t2 + 1;
                } else if ($sum % 2 == 1 || $sum % 2 == - 1) {
                    $t1 = $x + 2;
                    $t2 = $t1 + 5;
                    $t3 = $t2 - 1;
                }
                break;
            case - 1:
                if ($sum % 2 == 0) {
                    $t1 = $x + 12;
                    $t2 = $t1 - 6;
                    $t3 = $t2 + 1;
                } else if ($sum % 2 == 1 || $sum % 2 == - 1) {
                    $t1 = $x + 6;
                    $t2 = $t1 + 6;
                    $t3 = $t2 - 1;
                }
                break;
            case 0:
                if ($sum % 2 == 0) {
                    $t1 = $x + 18;
                    $t2 = $t1 - 7;
                    $t3 = $t2 + 1;
                } else if ($sum % 2 == 1) {
                    $t1 = $x + 11;
                    $t2 = $t1 + 7;
                    $t3 = $t2 - 1;
                }
                break;
            case 1:
                if ($sum % 2 == 0) {
                    $t1 = $x + 24;
                    $t2 = $t1 - 7;
                    $t3 = $t2 + 1;
                } else if ($sum % 2 == 1) {
                    $t1 = $x + 17;
                    $t2 = $t1 + 7;
                    $t3 = $t2 - 1;
                }
                break;
            case 2:
                if ($sum % 2 == 0) {
                    $t1 = $x + 29;
                    $t2 = $t1 - 6;
                    $t3 = $t2 + 1;
                } else if ($sum % 2 == 1) {
                    $t1 = $x + 23;
                    $t2 = $t1 + 6;
                    $t3 = $t2 - 1;
                }
                break;
            case 3:
                if ($sum % 2 == 0) {
                    $t1 = $x + 33;
                    $t2 = $t1 - 5;
                    $t3 = $t2 + 1;
                } else if ($sum % 2 == 1) {
                    $t1 = $x + 28;
                    $t2 = $t1 + 5;
                    $t3 = $t2 - 1;
                }
                break;
        }
        $result = pg_query(sprintf("INSERT INTO %s(stlmindex,x,y,z,tile1,tile2,tile3) VALUES(%d,%d,%d,%d,%d,%d,%d);", $table, $i, $x, $y, $z, $t1, $t2, $t3));
    }
}

function initRoadDB($game)
{
    $table = $game . ".roads";
    $vector = array(
        array(
            3,
            0
        ),
        array(
            0,
            4
        ),
        array(
            4,
            1
        ),
        array(
            1,
            5
        ),
        array(
            5,
            2
        ),
        array(
            2,
            6
        ),
        array(
            3,
            7
        ),
        array(
            4,
            8
        ),
        array(
            5,
            9
        ),
        array(
            6,
            10
        ),
        array(
            11,
            7
        ),
        array(
            7,
            12
        ),
        array(
            12,
            8
        ),
        array(
            8,
            13
        ),
        array(
            13,
            9
        ),
        array(
            9,
            14
        ),
        array(
            14,
            10
        ),
        array(
            10,
            15
        ),
        array(
            11,
            16
        ),
        array(
            12,
            17
        ),
        array(
            13,
            18
        ),
        array(
            14,
            19
        ),
        array(
            15,
            20
        ),
        array(
            21,
            16
        ),
        array(
            16,
            22
        ),
        array(
            22,
            17
        ),
        array(
            17,
            23
        ),
        array(
            23,
            18
        ),
        array(
            18,
            24
        ),
        array(
            24,
            19
        ),
        array(
            19,
            25
        ),
        array(
            25,
            20
        ),
        array(
            20,
            26
        ),
        array(
            21,
            27
        ),
        array(
            22,
            28
        ),
        array(
            23,
            29
        ),
        array(
            24,
            30
        ),
        array(
            25,
            31
        ),
        array(
            26,
            32
        ),
        array(
            27,
            33
        ),
        array(
            33,
            28
        ),
        array(
            28,
            34
        ),
        array(
            34,
            29
        ),
        array(
            29,
            35
        ),
        array(
            35,
            30
        ),
        array(
            30,
            36
        ),
        array(
            36,
            31
        ),
        array(
            31,
            37
        ),
        array(
            37,
            32
        ),
        array(
            33,
            38
        ),
        array(
            34,
            39
        ),
        array(
            35,
            40
        ),
        array(
            36,
            41
        ),
        array(
            37,
            42
        ),
        array(
            38,
            43
        ),
        array(
            43,
            39
        ),
        array(
            39,
            44
        ),
        array(
            44,
            40
        ),
        array(
            40,
            45
        ),
        array(
            45,
            41
        ),
        array(
            41,
            46
        ),
        array(
            46,
            42
        ),
        array(
            43,
            47
        ),
        array(
            44,
            48
        ),
        array(
            45,
            49
        ),
        array(
            46,
            50
        ),
        array(
            47,
            51
        ),
        array(
            51,
            48
        ),
        array(
            48,
            52
        ),
        array(
            52,
            49
        ),
        array(
            49,
            53
        ),
        array(
            53,
            50
        )
    );
    for ($i = 0; $i < 72; $i ++) {
        $result = pg_query(sprintf("INSERT INTO %s(roadindex,node1,node2) VALUES(%d,%d,%d);", $table, $i, $vector[$i][0], $vector[$i][1]));
        if (! $result) {
            die('failed to initroadDB');
        }
    }
}

function initDevDB($game)
{
    $table = $game . ".devs";
    $ary = array(
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'knight',
        'construct',
        'construct',
        'discover',
        'discover',
        'monopoly',
        'monopoly',
        'point',
        'point',
        'point',
        'point',
        'point'
    );
    for ($i = 0; $i < 25; $i ++) {
        $result = pg_query(sprintf("INSERT INTO %s VALUES(%d,'%s',%d);", $table, $i, $ary[$i], 0));
        if (! $result) {
            die("failed to insert to devs");
        }
    }
}
?>