package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sharding Sphinx")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SPHINX})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ShardingSphinx extends Card
{
	public static final class ArtifactBunnies extends EventTriggeredAbility
	{
		public ArtifactBunnies(GameState state)
		{
			super(state, "Whenever an artifact creature you control deals combat damage to a player, you may put a 1/1 blue Thopter artifact creature token with flying onto the battlefield.");
			this.addPattern(whenDealsCombatDamageToAPlayer(Intersect.instance(HasType.instance(Type.ARTIFACT), ControlledBy.instance(You.instance()))));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 blue Thopter artifact creature token with flying onto the battlefield");
			token.setColors(Color.BLUE);
			token.setSubTypes(SubType.THOPTER);
			token.setArtifact();
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(youMay(token.getEventFactory(), "You may put a 1/1 blue Thopter artifact creature token with flying onto the battlefield."));
		}
	}

	public ShardingSphinx(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new ArtifactBunnies(state));
	}
}
