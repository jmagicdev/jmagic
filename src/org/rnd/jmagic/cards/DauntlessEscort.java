package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Dauntless Escort")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.RHINO})
@ManaCost("1GW")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class DauntlessEscort extends Card
{
	public static final class ProtWrath extends ActivatedAbility
	{
		public ProtWrath(GameState state)
		{
			super(state, "Sacrifice Dauntless Escort: Creatures you control are indestructible this turn.");

			this.addCost(sacrificeThis("Dauntless Escort"));

			this.addEffect(createFloatingEffect("Creatures you control are indestructible this turn.", indestructible(CREATURES_YOU_CONTROL)));
		}
	}

	public DauntlessEscort(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new ProtWrath(state));
	}
}
