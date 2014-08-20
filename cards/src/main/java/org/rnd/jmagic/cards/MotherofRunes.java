package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mother of Runes")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class MotherofRunes extends Card
{
	public static final class MotherofRunesAbility0 extends ActivatedAbility
	{
		public MotherofRunesAbility0(GameState state)
		{
			super(state, "(T): Target creature you control gains protection from the color of your choice until end of turn.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance())), "target creature you control"));

			EventFactory chooseColor = playerChoose(You.instance(), 1, Identity.fromCollection(Color.allColors()), PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "");
			this.addEffect(chooseColor);

			SetGenerator color = EffectResult.instance(chooseColor);
			this.addEffect(addProtectionUntilEndOfTurn(target, color, "Target creature gains protection from the color of your choice until end of turn."));
		}
	}

	public MotherofRunes(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Target creature you control gains protection from the color of
		// your choice until end of turn.
		this.addAbility(new MotherofRunesAbility0(state));
	}
}
