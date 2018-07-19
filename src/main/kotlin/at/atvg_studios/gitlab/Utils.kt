package at.atvg_studios.gitlab

class Utils
{
    companion object {
        fun equal(a: Any, b: Any): Boolean
        {
            if(a is String && b is String)
            {
                if(a.equals(b, true))
                    return true
            }

            if(a == b)
                return true

            return false
        }

        fun equal(a: Any, b: Int, isHex: Boolean): Boolean
        {
            if(isHex)
                return equal(a, "0x"+Integer.toHexString(b))
            else
                return equal(a,b)
        }
    }
}