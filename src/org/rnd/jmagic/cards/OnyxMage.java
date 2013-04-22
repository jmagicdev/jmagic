package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Onyx Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class OnyxMage extends Card
{
	public static final class OnyxMageAbility0 extends ActivatedAbility
	{
		public OnyxMageAbility0(GameState state)
		{
			super(state, "(1)(B): Target creature you control gains deathtouch until end of turn.");
			this.setManaCost(new ManaPool("(1)(B)"));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Deathtouch.class, "Target creature you control gains deathtouch until end of turn."));
		}
	}

	public OnyxMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (1)(B): Target creature you control gains deathtouch until end of
		// turn. (Any amount of damage it deals to a creature is enough to
		// destroy it.)
		this.addAbility(new OnyxMageAbility0(state));
	}
}
