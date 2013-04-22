package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hellhole Flailer")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.WARRIOR})
@ManaCost("1BR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class HellholeFlailer extends Card
{
	public static final class HellholeFlailerAbility1 extends ActivatedAbility
	{
		public HellholeFlailerAbility1(GameState state)
		{
			super(state, "(2)(B)(R), Sacrifice Hellhole Flailer: Hellhole Flailer deals damage equal to its power to target player.");
			this.setManaCost(new ManaPool("(2)(B)(R)"));
			this.addCost(sacrificeThis("Hellhole Flailer"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(permanentDealDamage(PowerOf.instance(ABILITY_SOURCE_OF_THIS), target, "Hellhole Flailer deals damage equal to its power to target player."));
		}
	}

	public HellholeFlailer(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));

		// (2)(B)(R), Sacrifice Hellhole Flailer: Hellhole Flailer deals damage
		// equal to its power to target player.
		this.addAbility(new HellholeFlailerAbility1(state));
	}
}
