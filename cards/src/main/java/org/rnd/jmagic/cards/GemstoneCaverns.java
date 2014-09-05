package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gemstone Caverns")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class GemstoneCaverns extends Card
{
	public static final class GetLucky extends StaticAbility
	{
		public GetLucky(GameState state)
		{
			super(state, "If Gemstone Caverns is in your opening hand and you're not playing first, you may begin the game with Gemstone Caverns on the battlefield with a luck counter on it. If you do, exile a card from your hand.");
			this.canApply = RelativeComplement.instance(You.instance(), PlayingFirst.instance());

			EventFactory beginWithLuckCounter = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_WITH_COUNTERS, "Begin the game with Gemstone Caverns on the battlefield with a luck counter on it.");
			beginWithLuckCounter.parameters.put(EventType.Parameter.CAUSE, This.instance());
			beginWithLuckCounter.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			beginWithLuckCounter.parameters.put(EventType.Parameter.OBJECT, This.instance());
			beginWithLuckCounter.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.LUCK));

			EventFactory exileACard = exile(You.instance(), InZone.instance(HandOf.instance(You.instance())), 1, "Exile a card from your hand.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BEGIN_THE_GAME_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(beginWithLuckCounter));
			part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(exileACard));
			this.addEffectPart(part);
		}
	}

	public static final class GemstoneCavernsAbility1 extends ActivatedAbility
	{
		public GemstoneCavernsAbility1(GameState state)
		{
			super(state, "(T): Add (1) to your mana pool. If Gemstone Caverns has a luck counter on it, instead add one mana of any color to your mana pool.");
			this.costsTap = true;

			SetGenerator hasCounter = Intersect.instance(This.instance(), HasCounterOfType.instance(Counter.CounterType.LUCK));
			SetGenerator mana = IfThenElse.instance(hasCounter, Identity.fromCollection(new ManaPool("(WUBRG)")), Identity.fromCollection(new ManaPool("(1)")));

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add (1) to your mana pool. If Gemstone Caverns has a luck counter on it, instead add one mana of any color to your mana pool.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, mana);
			addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(addMana);
		}
	}

	public GemstoneCaverns(GameState state)
	{
		super(state);

		// If Gemstone Caverns is in your opening hand and you're not playing
		// first, you may begin the game with Gemstone Caverns on the
		// battlefield with a luck counter on it. If you do, exile a card from
		// your hand.
		this.addAbility(new GetLucky(state));

		// (T): Add (1) to your mana pool. If Gemstone Caverns has a luck
		// counter on it, instead add one mana of any color to your mana pool.
		this.addAbility(new GemstoneCavernsAbility1(state));
	}
}
