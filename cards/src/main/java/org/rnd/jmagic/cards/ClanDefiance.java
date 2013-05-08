package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Clan Defiance")
@Types({Type.SORCERY})
@ManaCost("XRG")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class ClanDefiance extends Card
{
	public ClanDefiance(GameState state)
	{
		super(state);

		// Choose one or more \u2014
		this.setNumModes(new Set(new org.rnd.util.NumberRange(1, null)));

		SetGenerator X = ValueOfX.instance(This.instance());
		SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);

		// Clan Defiance deals X damage to target creature with flying
		{
			SetGenerator target = targetedBy(this.addTarget(1, Intersect.instance(CreaturePermanents.instance(), hasFlying), "target creature with flying"));
			this.addEffect(1, spellDealDamage(X, target, "Clan Defiance deals X damage to target creature with flying"));
		}

		// Clan Defiance deals X damage to target creature without flying
		{
			SetGenerator target = targetedBy(this.addTarget(2, RelativeComplement.instance(CreaturePermanents.instance(), hasFlying), "target creature with flying"));
			this.addEffect(2, spellDealDamage(X, target, "Clan Defiance deals X damage to target creature without flying"));
		}

		// Clan Defiance deals X damage to target player.
		{
			SetGenerator target = targetedBy(this.addTarget(3, Players.instance(), "target player"));
			this.addEffect(3, spellDealDamage(X, target, "Clan Defiance deals X damage to target player."));
		}
	}
}
