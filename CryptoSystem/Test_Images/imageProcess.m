plainimg=imread("peppers.png");
encryptedimg=imread("encrypted.png");

[peaksnr, snr] = psnr(plainimg,encryptedimg);
  
fprintf('\n The Peak-SNR value is %0.4f', peaksnr);
err = immse(plainimg,encryptedimg);
fprintf('\n The mean-squared error is %0.4f\n', err);
imshow(plainimg);
%%
plainimg=imread("peppers.png");
encryptedimg=imread("decrypted.png");

[peaksnr, snr] = psnr(plainimg,encryptedimg);
  
fprintf('\n The Peak-SNR value is %0.4f', peaksnr);
err = immse(plainimg,encryptedimg);
fprintf('\n The mean-squared error is %0.4f\n', err);
imshow(plainimg);
%%
entropy(plainimg);
%%
plainimg=imread("lena.jpg");
imhist(plainimg(:,:,1));
title('Histogram(Redband) ; Lena.jpg');
%%
plainimg=imread("encrypted.jpg");
imhist(plainimg(:,:,1));
title('Histogram(Greenband) ; Compressed Deciphered ');
%%
plainimg=imread("lenaOriginal.png");
imhist(plainimg(1,:,:));
title('Histogram(Blueband) ; Compressed Deciphered ');
%%
plainimg=imread("lenaOriginal.png");
r=plainimg(:,:,1);
g=plainimg(:,:,2);
b=plainimg(:,:,3);
plot3(r(:),g(:),b(:),'.');
grid('on');
xlabel("red");
ylabel("green");
zlabel("blue");
%%
encryptedimg=imread("new.jpg");
r=encryptedimg(:,:,1);
g=encryptedimg(:,:,2);
b=encryptedimg(:,:,3);
plot3(r(:),g(:),b(:),'.');
grid('on');
xlabel("red");
ylabel("green");
zlabel("blue");
%%
encryptedimg=imread("new.jpg");
imhist(encryptedimg(:,:,1));
title('Histogram(Redband) ; Compressed Deciphered ');
%%
encryptedimg=imread("new.jpg");
imhist(encryptedimg(:,1,:));
title('Histogram(Greenband) ; Compressed Deciphered ');
%%
encryptedimg=imread("new.jpg");
imhist(encryptedimg(1,:,:));
title('Histogram(Blueband) ; Compressed Deciphered ');

