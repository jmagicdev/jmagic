package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Seeker of the Way")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SeekeroftheWay extends Card
{
	public static final class SeekeroftheWayAbility1 extends EventTriggeredAbility
	{
		public SeekeroftheWayAbility1(GameState state)
		{
			super(state, "Whenever you cast a noncreature spell, Seeker of the Way gains lifelink until end of turn.");
			this.addPattern(whenYouCastANoncreatureSpell());
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Seeker of the Way gains lifelink until end of turn."));
		}
	}

	public SeekeroftheWay(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));

		// Whenever you cast a noncreature spell, Seeker of the Way gains
		// lifelink until end of turn.
		this.addAbility(new SeekeroftheWayAbility1(state));
	}
}
