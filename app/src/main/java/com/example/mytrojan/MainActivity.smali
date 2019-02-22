.class public Lcom/example/mytrojan/MainActivity;
.super Landroid/support/v7/app/AppCompatActivity;
.source "MainActivity.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 6
    invoke-direct {p0}, Landroid/support/v7/app/AppCompatActivity;-><init>()V

    return-void
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .registers 3
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 10
    invoke-super {p0, p1}, Landroid/support/v7/app/AppCompatActivity;->onCreate(Landroid/os/Bundle;)V

    .line 11
    new-instance v0, Lcom/example/mytrojan/ExecuteIt;

    invoke-direct {v0, p0}, Lcom/example/mytrojan/ExecuteIt;-><init>(Landroid/content/Context;)V

    .line 12
    return-void
.end method
