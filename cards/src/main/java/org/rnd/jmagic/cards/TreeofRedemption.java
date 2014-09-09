package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tree of Redemption")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class TreeofRedemption extends Card
{
	public static final class TreeofRedemptionAbility1 extends ActivatedAbility
	{
		public TreeofRedemptionAbility1(GameState state)
		{
			super(state, "(T): Exchange your life total with Tree of Redemption's toughness.");
			this.costsTap = true;

			EventFactory noteLife = new EventFactory(NOTE, "");
			noteLife.parameters.put(EventType.Parameter.OBJECT, LifeTotalOf.instance(You.instance()));

			EventFactory noteToughness = new EventFactory(NOTE, "");
			noteToughness.parameters.put(EventType.Parameter.OBJECT, ToughnessOf.instance(ABILITY_SOURCE_OF_THIS));

			SetGenerator newLifeTotal = EffectResult.instance(noteToughness);
			EventFactory setLife = new EventFactory(EventType.SET_LIFE, "Your life total becomes Tree of Redemption's toughness.");
			setLife.parameters.put(EventType.Parameter.CAUSE, This.instance());
			setLife.parameters.put(EventType.Parameter.PLAYER, You.instance());
			setLife.parameters.put(EventType.Parameter.NUMBER, newLifeTotal);

			SetGenerator newToughness = EffectResult.instance(noteLife);
			ContinuousEffect.Part part = setPowerAndToughness(ABILITY_SOURCE_OF_THIS, null, newToughness);
			EventFactory setToughness = createFloatingEffect("Tree of Redemption's toughness becomes your life total.", part);
			setToughness.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));

			EventFactory exchange = sequence(noteLife, noteToughness, simultaneous(setLife, setToughness));

			// An exchange can't happen unless both numbers exist.
			SetGenerator thisIsAlive = Intersect.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS);
			// you are alive -- this trigger is resolving...
			this.addEffect(ifThen(thisIsAlive, exchange, "Exchange your life total with Tree of Redemption's Toughness."));
		}
	}

	public TreeofRedemption(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(13);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (T): Exchange your life total with Tree of Redemption's toughness.
		this.addAbility(new TreeofRedemptionAbility1(state));
	}
}
