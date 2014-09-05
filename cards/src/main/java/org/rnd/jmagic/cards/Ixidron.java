package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ixidron")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Ixidron extends Card
{
	public static final class NotQuiteWrath extends StaticAbility
	{
		public NotQuiteWrath(GameState state)
		{
			super(state, "As Ixidron enters the battlefield, turn all other nontoken creatures face down.");
			this.canApply = NonEmpty.instance();

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "As Ixidron enters the battlefield, turn all other nontoken creatures face down.");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator replacedMove = replacement.replacedByThis();
			SetGenerator nontokenCreatures = Intersect.instance(CreaturePermanents.instance(), NonToken.instance());
			SetGenerator otherNontokenCreatures = RelativeComplement.instance(nontokenCreatures, NewObjectOf.instance(replacedMove));

			EventFactory effect = new EventFactory(EventType.TURN_PERMANENTS_FACE_DOWN, "Turn all other nontoken creatures face down");
			effect.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(replacedMove));
			effect.parameters.put(EventType.Parameter.OBJECT, otherNontokenCreatures);
			replacement.addEffect(effect);

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public static final class PowerToughnessFaceDown extends CharacteristicDefiningAbility
	{
		public PowerToughnessFaceDown(GameState state)
		{
			super(state, "Ixidron's power and toughness are each equal to the number of face-down creatures on the battlefield.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator faceDownCreatures = Count.instance(Intersect.instance(FaceDown.instance(), CreaturePermanents.instance()));
			this.addEffectPart(setPowerAndToughness(This.instance(), faceDownCreatures, faceDownCreatures));
		}
	}

	public Ixidron(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// As Ixidron enters the battlefield, turn all other nontoken creatures
		// face down. (They're 2/2 creatures.)
		this.addAbility(new NotQuiteWrath(state));

		// Ixidron's power and toughness are each equal to the number of
		// face-down creatures on the battlefield.
		this.addAbility(new PowerToughnessFaceDown(state));
	}
}
