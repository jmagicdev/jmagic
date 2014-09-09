package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Graveyard Shovel")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class GraveyardShovel extends Card
{
	public static final class GraveyardShovelAbility0 extends ActivatedAbility
	{
		public GraveyardShovelAbility0(GameState state)
		{
			super(state, "(2), (T): Target player exiles a card from his or her graveyard. If it's a creature card, you gain 2 life.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			SetGenerator cardsInGraveyard = Intersect.instance(Cards.instance(), InZone.instance(GraveyardOf.instance(target)));
			EventFactory exile = exile(target, cardsInGraveyard, 1, "Target player exiles a card from his or her graveyard.");
			this.addEffect(exile);

			EventFactory gainLife = gainLife(You.instance(), 2, "You gain 2 life");

			SetGenerator exiledCard = OldObjectOf.instance(EffectResult.instance(exile));
			EventFactory ifCreatureGainLife = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's a creature card, you gain 2 life.");
			ifCreatureGainLife.parameters.put(EventType.Parameter.IF, Intersect.instance(exiledCard, HasType.instance(Type.CREATURE)));
			ifCreatureGainLife.parameters.put(EventType.Parameter.THEN, Identity.instance(gainLife));
			this.addEffect(ifCreatureGainLife);
		}
	}

	public GraveyardShovel(GameState state)
	{
		super(state);

		// (2), (T): Target player exiles a card from his or her graveyard. If
		// it's a creature card, you gain 2 life.
		this.addAbility(new GraveyardShovelAbility0(state));
	}
}
