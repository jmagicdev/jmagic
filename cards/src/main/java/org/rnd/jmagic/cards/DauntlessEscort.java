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
			super(state, "Sacrifice Dauntless Escort: Creatures you control gain indestructible until end of turn.");

			this.addCost(sacrificeThis("Dauntless Escort"));

			this.addEffect(createFloatingEffect("Creatures you control gain indestructible until end of turn.", addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Indestructible.class)));
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
