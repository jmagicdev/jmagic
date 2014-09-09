package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Victory's Herald")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WWW")
@ColorIdentity({Color.WHITE})
public final class VictorysHerald extends Card
{
	public static final class VictorysHeraldAbility1 extends EventTriggeredAbility
	{
		public VictorysHeraldAbility1(GameState state)
		{
			super(state, "Whenever Victory's Herald attacks, attacking creatures gain flying and lifelink until end of turn.");
			this.addPattern(whenThisAttacks());
			this.addEffect(createFloatingEffect("Attacking creatures gain flying and lifelink until end of turn.", addAbilityToObject(Attacking.instance(), org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.Lifelink.class)));
		}
	}

	public VictorysHerald(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Victory's Herald attacks, attacking creatures gain flying
		// and lifelink until end of turn.
		this.addAbility(new VictorysHeraldAbility1(state));
	}
}
