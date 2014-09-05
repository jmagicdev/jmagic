package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bridge from Below")
@Types({Type.ENCHANTMENT})
@ManaCost("BBB")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class BridgefromBelow extends Card
{
	public static final class MakeTokens extends EventTriggeredAbility
	{
		public MakeTokens(GameState state)
		{
			super(state, "Whenever a nontoken creature is put into your graveyard from the battlefield, if Bridge from Below is in your graveyard, put a 2/2 black Zombie creature token onto the battlefield.");
			this.triggersFromGraveyard();
			this.interveningIf = Intersect.instance(ABILITY_SOURCE_OF_THIS, InZone.instance(GraveyardOf.instance(You.instance())));

			// TODO : Singleton-generator for this?
			SetGenerator nontokenCreatures = RelativeComplement.instance(CreaturePermanents.instance(), Tokens.instance());

			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(You.instance()), nontokenCreatures, true));

			CreateTokensFactory token = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.ZOMBIE);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class KillBridge extends EventTriggeredAbility
	{
		public KillBridge(GameState state)
		{
			super(state, "When a creature is put into an opponent's graveyard from the battlefield, if Bridge from Below is in your graveyard, exile Bridge from Below.");
			this.triggersFromGraveyard();
			this.interveningIf = Intersect.instance(ABILITY_SOURCE_OF_THIS, InZone.instance(GraveyardOf.instance(You.instance())));

			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(OpponentsOf.instance(You.instance())), CreaturePermanents.instance(), true));

			this.addEffect(exile(ABILITY_SOURCE_OF_THIS, "Exile Bridge from Below."));
		}
	}

	public BridgefromBelow(GameState state)
	{
		super(state);

		// Whenever a nontoken creature is put into your graveyard from the
		// battlefield, if Bridge from Below is in your graveyard, put a 2/2
		// black Zombie creature token onto the battlefield.
		this.addAbility(new MakeTokens(state));

		// When a creature is put into an opponent's graveyard from the
		// battlefield, if Bridge from Below is in your graveyard, exile Bridge
		// from Below.
		this.addAbility(new KillBridge(state));
	}
}
