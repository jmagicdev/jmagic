package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Horror of the Dim")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class HorroroftheDim extends Card
{
	public static final class HorroroftheDimAbility0 extends ActivatedAbility
	{
		public HorroroftheDimAbility0(GameState state)
		{
			super(state, "(U): Horror of the Dim gains hexproof until end of turn.");
			this.setManaCost(new ManaPool("(U)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Hexproof.class, "Horror of the Dim gains hexproof until end of turn."));
		}
	}

	public HorroroftheDim(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// (U): Horror of the Dim gains hexproof until end of turn. (It can't be
		// the target of spells or abilities your opponents control.)
		this.addAbility(new HorroroftheDimAbility0(state));
	}
}
