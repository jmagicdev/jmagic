package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shadowcloak Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class ShadowcloakVampire extends Card
{
	public static final class ShadowcloakVampireAbility0 extends ActivatedAbility
	{
		public ShadowcloakVampireAbility0(GameState state)
		{
			super(state, "Pay 2 life: Shadowcloak Vampire gains flying until end of turn.");
			this.addCost(payLife(You.instance(), 2, "Pay 2 life."));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Shadowcloak Vampire gains flying until end of turn."));
		}
	}

	public ShadowcloakVampire(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Pay 2 life: Shadowcloak Vampire gains flying until end of turn. (It
		// can't be blocked except by creatures with flying or reach.)
		this.addAbility(new ShadowcloakVampireAbility0(state));
	}
}
