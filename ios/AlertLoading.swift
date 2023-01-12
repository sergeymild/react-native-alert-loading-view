import NVActivityIndicatorView


@objc(AlertLoading)
class AlertLoading: RCTViewManager {
    var presentedView: UIView?
    var isFadingOut = false
    var isPresenting = false
    var requestedHide = false
    
    override class func requiresMainQueueSetup() -> Bool {
        return true
    }

    @objc
    func showLoading(_ params: NSDictionary) {
        if presentedView != nil { return }
        if isPresenting { return }
        isPresenting = true
        debugPrint("AlertLoading.showLoading")
        let size = CGSize(width: 42, height: 52)
        DispatchQueue.main.async { [weak self] in
            if self?.presentedView != nil { return }
            
            let containerView = UIView(frame: UIScreen.main.bounds)
            
            let overlayColor = RCTConvert.uiColor(params["overlayColor"])
            let color = RCTConvert.uiColor(params["color"])
            
            containerView.backgroundColor = overlayColor ?? NVActivityIndicatorView.DEFAULT_BLOCKER_BACKGROUND_COLOR
            containerView.restorationIdentifier = "NVActivityIndicatorViewContainer"
            containerView.translatesAutoresizingMaskIntoConstraints = false
            
            
            var type: NVActivityIndicatorType = .audioEqualizer
            if let newType = params["type"] as? String {
                switch newType {
                case "circleStrokeSpin":
                    type = .circleStrokeSpin
                case "ballScaleRippleMultiple":
                    type = .ballScaleMultiple
                case "ballSpinFadeLoader":
                    type = .ballSpinFadeLoader
                default:
                    type = .audioEqualizer
                }
            }
            
            let activityIndicatorView = NVActivityIndicatorView(
                frame: .init(origin: .zero, size: size),
                type: type,
                color: color ?? NVActivityIndicatorView.DEFAULT_COLOR,
                padding: 0
            )
            activityIndicatorView.startAnimating()
            if type == .circleStrokeSpin {
                (activityIndicatorView.layer.sublayers?[0] as? CAShapeLayer)?.lineWidth = 4
            }
            activityIndicatorView.translatesAutoresizingMaskIntoConstraints = false
            containerView.addSubview(activityIndicatorView)
            
            // Add constraints for `activityIndicatorView`.
            ({
                let xConstraint = NSLayoutConstraint(item: containerView, attribute: .centerX, relatedBy: .equal, toItem: activityIndicatorView, attribute: .centerX, multiplier: 1, constant: 0)
                let yConstraint = NSLayoutConstraint(item: containerView, attribute: .centerY, relatedBy: .equal, toItem: activityIndicatorView, attribute: .centerY, multiplier: 1, constant: 0)

                containerView.addConstraints([xConstraint, yConstraint])
                }())
            
            guard let keyWindow = UIApplication.shared.keyWindow else { return }
            keyWindow.addSubview(containerView)
            
            // Add constraints for `containerView`.
            ({
                let leadingConstraint = NSLayoutConstraint(item: keyWindow, attribute: .leading, relatedBy: .equal, toItem: containerView, attribute: .leading, multiplier: 1, constant: 0)
                let trailingConstraint = NSLayoutConstraint(item: keyWindow, attribute: .trailing, relatedBy: .equal, toItem: containerView, attribute: .trailing, multiplier: 1, constant: 0)
                let topConstraint = NSLayoutConstraint(item: keyWindow, attribute: .top, relatedBy: .equal, toItem: containerView, attribute: .top, multiplier: 1, constant: 0)
                let bottomConstraint = NSLayoutConstraint(item: keyWindow, attribute: .bottom, relatedBy: .equal, toItem: containerView, attribute: .bottom, multiplier: 1, constant: 0)

                keyWindow.addConstraints([leadingConstraint, trailingConstraint, topConstraint, bottomConstraint])
                }())
            
            if (params["animate"] as? Bool) == true {
                containerView.alpha = 0
                UIView.animate(withDuration: 0.25) {
                    containerView.alpha = 1
                } completion: { _ in
                    debugPrint("AlertLoading.presented")
                    self?.isPresenting = false
                    if self?.requestedHide == true { self?.hideLoading([:]) }
                }
            } else {
                self?.isPresenting = false
                debugPrint("AlertLoading.presented")
                if self?.requestedHide == true { self?.hideLoading([:]) }
            }
            self?.presentedView = containerView
        }
    }
    
    @objc
    func hideLoading(_ params: NSDictionary?) {
        if self.presentedView == nil {
            requestedHide = true
            return
        }
        if isFadingOut { return }
        isFadingOut = true
        debugPrint("AlertLoading.hideLoading")
        DispatchQueue.main.async { [weak self] in
            guard let self = self, let view = self.presentedView else {
                self?.isFadingOut = false
                return
            }
            if (params?["animate"] as? Bool) == false {
                NVActivityIndicatorView.DEFAULT_FADE_OUT_ANIMATION(view) {
                    view.removeFromSuperview()
                    self.presentedView = nil
                    self.isFadingOut = false
                    debugPrint("AlertLoading.hidden")
                }
            } else {
                view.removeFromSuperview()
                self.presentedView = nil
                self.isFadingOut = false
                debugPrint("AlertLoading.hidden")
            }
            
        }
    }
}
