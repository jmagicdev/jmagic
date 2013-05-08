package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chrome Mox")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class ChromeMox extends Card
{
	public static final class ChromeMoxImprint extends EventTriggeredAbility
	{
		public ChromeMoxImprint(GameState state)
		{
			super(state, "When Chrome Mox enters the battlefield, you may exile a nonartifact, nonland card from your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "Exile a nonartifact, nonland card from your hand.");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			exile.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(InZone.instance(HandOf.instance(You.instance())), HasType.instance(Type.ARTIFACT, Type.LAND)));
			exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
			SetGenerator exileEvent = Identity.instance(exile);

			EventType.ParameterMap mayParameters = new EventType.ParameterMap();
			mayParameters.put(EventType.Parameter.PLAYER, You.instance());
			// Union with This to store link information here.
			// TODO : WTF? How is this different from Champion's "you may exile"
			// effect? Why can't we just use exile.setLinkClass? -RulesGuru
			mayParameters.put(EventType.Parameter.EVENT, Union.instance(exileEvent, This.instance()));
			this.addEffect(new EventFactory(EventType.PLAYER_MAY, mayParameters, "You may exile a nonartifact, nonland card from your hand."));

			this.getLinkManager().addLinkClass(ImprintMana.class);
		}
	}

	public static final class ImprintMana extends ActivatedAbility
	{
		public ImprintMana(GameState state)
		{
			super(state, "(T): Add one mana of any of the exiled card's colors to your mana pool.");

			this.costsTap = true;

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			parameters.put(EventType.Parameter.MANA, ColorsOf.instance(ChosenFor.instance(LinkedTo.instance(This.instance()))));
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.ADD_MANA, parameters, "Add one mana of any of the exiled card's colors to your mana pool."));

			this.getLinkManager().addLinkClass(ChromeMoxImprint.class);
		}
	}

	public ChromeMox(GameState state)
	{
		super(state);

		this.addAbility(new ChromeMoxImprint(state));
		this.addAbility(new ImprintMana(state));
	}
}
