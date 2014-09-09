package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Charmbreaker Devils")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class CharmbreakerDevils extends Card
{
	public static final class CharmbreakerDevilsAbility0 extends EventTriggeredAbility
	{
		public CharmbreakerDevilsAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, return an instant or sorcery card at random from your graveyard to your hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator instantOrSorcery = HasType.instance(Type.INSTANT, Type.SORCERY);
			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator inGraveyard = Intersect.instance(InZone.instance(yourGraveyard), instantOrSorcery);
			EventFactory random = random(inGraveyard, "Randomly identify an instant or sorcery card in your graveyard");
			this.addEffect(random);

			EventFactory ret = new EventFactory(EventType.MOVE_OBJECTS, "Return that card from your graveyard to your hand");
			ret.parameters.put(EventType.Parameter.CAUSE, This.instance());
			ret.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			ret.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(random));
			this.addEffect(ret);
		}
	}

	public static final class CharmbreakerDevilsAbility1 extends EventTriggeredAbility
	{
		public CharmbreakerDevilsAbility1(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, Charmbreaker Devils gets +4/+0 until end of turn.");

			SetGenerator instantOrSorcery = HasType.instance(Type.INSTANT, Type.SORCERY);
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(instantOrSorcery, Spells.instance()));
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +4, +0, "Charmbreaker Devils gets +4/+0 until end of turn."));
		}
	}

	public CharmbreakerDevils(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// At the beginning of your upkeep, return an instant or sorcery card at
		// random from your graveyard to your hand.
		this.addAbility(new CharmbreakerDevilsAbility0(state));

		// Whenever you cast an instant or sorcery spell, Charmbreaker Devils
		// gets +4/+0 until end of turn.
		this.addAbility(new CharmbreakerDevilsAbility1(state));
	}
}
