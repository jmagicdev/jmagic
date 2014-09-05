package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Thada Adel, Acquisitor")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.MERFOLK})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ThadaAdelAcquisitor extends Card
{
	public static final class ThadaAdelAcquisitorAbility1 extends EventTriggeredAbility
	{
		public ThadaAdelAcquisitorAbility1(GameState state)
		{
			super(state, "Whenever Thada Adel, Acquisitor deals combat damage to a player, search that player's library for an artifact card and exile it. Then that player shuffles his or her library. Until end of turn, you may play that card.");
			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			EventFactory searchFactory = new EventFactory(EventType.SEARCH, "Search that player's library for an artifact card.");
			searchFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			searchFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			searchFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			searchFactory.parameters.put(EventType.Parameter.CARD, LibraryOf.instance(thatPlayer));
			searchFactory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.ARTIFACT)));
			this.addEffect(searchFactory);

			EventFactory exileFactory = exile(EffectResult.instance(searchFactory), "Exile it.");
			this.addEffect(exileFactory);

			ContinuousEffect.Part playEffect = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			playEffect.parameters.put(ContinuousEffectType.Parameter.OBJECT, NewObjectOf.instance(EffectResult.instance(exileFactory)));
			playEffect.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffect(createFloatingEffect("Until end of turn, you may play that card.", playEffect));
		}
	}

	public ThadaAdelAcquisitor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Islandwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));

		// Whenever Thada Adel, Acquisitor deals combat damage to a player,
		// search that player's library for an artifact card and exile it. Then
		// that player shuffles his or her library. Until end of turn, you may
		// play that card.
		this.addAbility(new ThadaAdelAcquisitorAbility1(state));
	}
}
