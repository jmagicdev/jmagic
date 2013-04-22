package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Screeching Silcaw")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ScreechingSilcaw extends Card
{
	public static final class ScreechingSilcawAbility1 extends EventTriggeredAbility
	{
		public ScreechingSilcawAbility1(GameState state)
		{
			super(state, "Whenever Screeching Silcaw deals combat damage to a player, if you control three or more artifacts, that player puts the top four cards of his or her library into his or her graveyard.");
			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));
			this.interveningIf = Metalcraft.instance();
			this.addEffect(millCards(TakerOfDamage.instance(TriggerDamage.instance(This.instance())), 4, "That player puts the top four cards of his or her library into his or her graveyard."));
		}
	}

	public ScreechingSilcaw(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Metalcraft \u2014 Whenever Screeching Silcaw deals combat damage to a
		// player, if you control three or more artifacts, that player puts the
		// top four cards of his or her library into his or her graveyard.
		this.addAbility(new ScreechingSilcawAbility1(state));
	}
}
